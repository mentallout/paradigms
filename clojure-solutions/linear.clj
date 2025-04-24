(defn vectorOperations [operation & vectors]
  (apply mapv operation vectors))

(def v+ (partial vectorOperations +))
(def v- (partial vectorOperations -))
(def v* (partial vectorOperations *))
(def vd (partial vectorOperations /))

(defn scalar [& vectors]
  (apply + (apply mapv * vectors)))

(defn determinant [x y]
  (let [x0 (nth x 0) y0 (nth x 1) z0 (nth x 2)
        x1 (nth y 0) y1 (nth y 1) z1 (nth y 2)]
    [(- (* y0 z1) (* z0 y1))
     (- (* z0 x1) (* x0 z1))
     (- (* x0 y1) (* y0 x1))]))

(defn vect [& vectors]
  (reduce determinant vectors))

(defn v*s [vector & scalars]
  (mapv #(* %1 (apply * (or scalars [1]))) vector))

(defn matrixOperations [operation & matrices]
  (apply mapv (partial mapv operation) matrices))

(def m+ (partial vectorOperations v+))
(def m- (partial matrixOperations -))
(def m* (partial matrixOperations *))
(def md (partial matrixOperations /))

(defn m*v [matrix & vectors]
  (mapv #(apply scalar % vectors) matrix))

(defn transpose [matrix]
  (apply mapv vector matrix))

(defn m*m
  [& matrices]
  (cond
    (empty? matrices) nil
    (= 1 (count matrices)) (first matrices)
    (= (count matrices) 2) (apply (fn [matrix1 matrix2] (mapv #(m*v (transpose matrix2) %) matrix1)) matrices)
    :else (apply m*m (cons (m*m (first matrices) (second matrices)) (drop 2 matrices)))))

(defn m*s [matrix & scalars]
  (mapv #(apply v*s % scalars) matrix))

(defn cubeOperations [operation & cubes]
  (apply mapv (fn [& rows] (apply mapv (fn [& columns] (apply mapv operation columns)) rows)) cubes))

(def c+ (partial cubeOperations +))
(def c- (partial cubeOperations -))
(def c* (partial cubeOperations *))
(def cd (partial cubeOperations /))
