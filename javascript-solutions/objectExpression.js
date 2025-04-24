'use strict';

const unaryOperators = {
    'negate': (x) => new Negate(x), 'sin': (x) => new Sin(x), 'cos': (x) => new Cos(x)
};

const binaryOperators = {
    '+': (x, y) => new Add(x, y),
    '-': (x, y) => new Subtract(x, y),
    '*': (x, y) => new Multiply(x, y),
    '/': (x, y) => new Divide(x, y)
};

const multiaryOperators = {
    'mean': (...args) => new Mean(...args), 'var': (...args) => new Var(...args)
};

function checkBrackets(expression) {
    let count = 0;
    for (let i = 0; i < expression.length; i++) {
        if (expression[i] === '(') {
            count++;
        } else if (expression[i] === ')') {
            count--;
            if (count < 0) {
                throw new CustomError("Extra closing bracket");
            }
        }
    }
    if (count !== 0) {
        throw new CustomError("Extra opening bracket");
    }
    return expression.split('(').join(' ( ').split(')').join(' ) ');
}

function getTokens(expression) {
    const stack = [];
    expression.split(' ').filter(token => token.length > 0).forEach(token => {
        if (token in unaryOperators || token in binaryOperators || token in multiaryOperators || ['x', 'y', 'z', '(', ')'].includes(token) || !Number.isNaN(Number(token)) && Number.isInteger(parseFloat(token.toString()))) {
            stack.push(token);
        } else {
            throw new CustomError("Unexpected token");
        }
    });
    return stack;
}

function parsing(type, expression) {
    let stack = [];
    let tokens = getTokens(checkBrackets(expression));
    if (tokens[0] === '(') {
        tokens.shift();
    }
    if (tokens[tokens.length - 1] === ')') {
        tokens.pop();
    }
    if (type) {
        tokens.reverse();
    }
    for (let i = 0; i < tokens.length; i++) {
        const token = tokens[i];
        console.error(token);
        if (token in unaryOperators) {
            stack.push(unaryOperators[token](stack.pop()));
        } else if (token in binaryOperators) {
            const x = stack.pop();
            const y = stack.pop();
            if (x === undefined || y === undefined || x === '(' || y === ')') {

                throw new CustomError("Not enough operands for " + token);
            }
            if (type) {
                stack.push(binaryOperators[token](x, y));
            } else {
                stack.push(binaryOperators[token](y, x));
            }
        } else if (token in multiaryOperators) {
            const values = [];
            let element = stack.pop();
            while (element !== undefined && element !== ')') {
                values.push(element);
                element = stack.pop();
            }
            stack.push(multiaryOperators[token](...values));
            i++;
        } else if (['x', 'y', 'z'].includes(token)) {
            stack.push(new Variable(token));
        } else if (!Number.isNaN(Number(token)) && Number.isInteger(parseFloat(token.toString()))) {
            stack.push(new Const(parseInt(token)));
        } else if (token === ')') {
            stack.push(token);
        } else if (token === '(') {
            const elements = [];
            let element = stack.pop();
            while (element !== ')' && element !== undefined) {
                elements.push(element);
                element = stack.pop();
            }
            stack.push(parse(elements.toString()));
        } else {
            throw new CustomError("Parsing error");
        }
    }
    if (stack.length !== 1) {
        throw new CustomError("Invalid expression");
    }
    return stack.pop();
}

function parse(expression) {
    return parsing(0, expression);
}

function parsePrefix(expression) {
    return parsing(1, expression);
}

function Const(value) {
    this.value = value;
}

Const.prototype.evaluate = function calc() {
    return this.value;
};
Const.prototype.toString = function toStr() {
    return this.value.toString();
};
Const.prototype.prefix = function prefix() {
    return this.value.toString();
};

function Variable(variable) {
    this.variable = variable;
}

Variable.prototype.evaluate = function calc(x, y, z) {
    if (this.variable === "x") {
        return x;
    }
    if (this.variable === "y") {
        return y;
    }
    if (this.variable === "z") {
        return z;
    }
};
Variable.prototype.toString = function toStr() {
    return this.variable.toString();
};
Variable.prototype.prefix = function prefix() {
    return this.variable.toString();
};

function BinaryOperation(left, right, operation, operator) {
    this.left = left;
    this.right = right;
    this.operation = operation;
    this.operator = operator;
}

BinaryOperation.prototype.evaluate = function calc(x, y, z) {
    return this.operation(this.left.evaluate(x, y, z), this.right.evaluate(x, y, z));
};
BinaryOperation.prototype.toString = function toStr() {
    return this.left.toString() + " " + this.right.toString() + " " + this.operator;
};
BinaryOperation.prototype.prefix = function prefix() {
    return "(" + this.operator + " " + this.left.prefix().toString() + " " + this.right.prefix().toString() + ")";
};

const getBinaryOperation = (operation, operator) => {
    return function calculating(left, right) {
        return new BinaryOperation(left, right, operation, operator);
    };
};

const Add = getBinaryOperation((x, y) => x + y, "+");
const Subtract = getBinaryOperation((x, y) => x - y, "-");
const Multiply = getBinaryOperation((x, y) => x * y, "*");
const Divide = getBinaryOperation((x, y) => x / y, "/");

function UnaryOperation(expression, operation, operator) {
    this.expression = expression;
    this.operation = operation;
    this.operator = operator;
}

UnaryOperation.prototype.evaluate = function calc(x, y, z) {
    return this.operation(this.expression.evaluate(x, y, z));
};
UnaryOperation.prototype.toString = function toStr() {
    return this.expression.toString() + " " + this.operator;
};
UnaryOperation.prototype.prefix = function prefix() {
    return "(" + this.operator + " " + this.expression.prefix() + ")";
};

const getUnaryOperation = (operation, operator) => {
    return function calculating(expression) {
        return new UnaryOperation(expression, operation, operator);
    };
};

const Negate = getUnaryOperation(expression => (-1) * expression, "negate");
const Sin = getUnaryOperation(expression => Math.sin(expression), "sin");
const Cos = getUnaryOperation(expression => Math.cos(expression), "cos");

function Mean(...args) {
    this.args = args;
}

Mean.prototype.evaluate = function calc(...args) {
    if (this.args.length === 0) {
        throw new CustomError("There should be at least one argument");
    }
    return this.args.reduce((sum, current) => sum + current.evaluate(...args), 0) / this.args.length;
};
Mean.prototype.toString = function toStr() {
    return "(" + this.args.map(arg => arg.toString()).join(' ') + " mean)";
};
Mean.prototype.prefix = function prefix() {
    return "(mean " + this.args.map(arg => arg.prefix()).join(' ') + ")";
};

function Var(...args) {
    this.args = args;
}

Var.prototype.evaluate = function calc(...args) {
    if (this.args.length === 0) {
        throw new CustomError("There should be at least one argument");
    }
    return this.args.reduce((result, current) => result + Math.pow(current.evaluate(...args) - this.args.reduce((sum, current) => sum + current.evaluate(...args), 0) / this.args.length, 2), 0) / this.args.length;
};
Var.prototype.toString = function toStr() {
    return "(" + this.args.map(arg => arg.toString()).join(' ') + " var)";
};
Var.prototype.prefix = function prefix() {
    return "(var " + this.args.map(arg => arg.prefix()).join(' ') + ")";
};