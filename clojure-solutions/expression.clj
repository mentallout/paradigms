(defn constant [const]
  (fn [_] const))

(defn variable [variable]
  (fn [args]
    (args variable)))

(defn unary [operation value]
  (fn [args]
    (operation (value args))))

(def negate (partial unary -))
(def sin (partial unary #(Math/sin %)))
(def cos (partial unary #(Math/cos %)))

(defn binary [operation & operands]
  (fn [args]
    (let [values (map #(% args) operands)]
      (apply operation values))))

(def add (partial binary +))
(def subtract (partial binary -))
(def multiply (partial binary *))
(def divide (partial binary (fn ([uno] (/ 1.0 uno))
                              ([first & rest] (reduce #(/ (double %1) %2) first rest)))))

(defn parsing [expression vars operations]
  (cond
    (symbol? expression) ((vars 0) (str expression))
    (number? expression) ((vars 1) expression)
    :else (let [op (operations (first expression))
                args (map #(parsing % vars operations) (rest expression))]
            (apply op args))))

(defn tokenize [string]
  (read-string string))

(defn parseFunction [args]
  (parsing (tokenize (str args)) [variable, constant] {'+ add '- subtract '* multiply '/ divide 'negate negate 'sin sin 'cos cos}))

(defn evaluate [expression vars] ((.evaluate expression) vars))
(defn toString [expression] (str expression))
(defn toStringPostfix [expression] (toString expression))

(definterface Functions
  (evaluate [])
  (toString [expr])
  (toStringPostfix []))

(deftype Const [value]
  Functions
  (evaluate [_] (fn [_] value))
  (toString [_] (format "%.1f" value))
  (toStringPostfix [_] (format "%.1f" value)))

(defn Constant [value] (Const. value))

(deftype Var [value]
  Functions
  (evaluate [_] (fn [args]
                  (get args value)))
  (toString [_] value)
  (toStringPostfix [_] value))

(defn Variable [value] (Var. value))

(deftype Operation [operation expression operator]
  Functions
  (evaluate [_]
    (fn [args] (->> expression
                    (map #(evaluate % args))
                    (apply operation))))
  (toString [_]
    (str "(" operator " " (clojure.string/join " " (map #(toString %) expression)) ")"))
  (toStringPostfix [_]
    (str "(" (clojure.string/join " " (map #(toString %) expression)) " " operator ")")))

(defn getOperation [operation operator]
  (fn [& expression] (Operation. operation expression operator)))

(def Add (getOperation + "+"))
(def Subtract (getOperation - "-"))
(def Multiply (getOperation * "*"))
(def Divide (getOperation (fn ([uno] (/ 1.0 uno))
                            ([first & rest] (reduce #(/ (double %1) %2) first rest))) "/"))
(def Negate (getOperation - "negate"))
(def Exp (getOperation #(Math/exp %) "exp"))
(def Ln (getOperation #(Math/log %) "ln"))

(defn parseObject [args]
  (parsing (tokenize (str args)) [Variable, Constant] {'+ Add '- Subtract '* Multiply '/ Divide 'negate Negate 'exp Exp 'ln Ln}))
