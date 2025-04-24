'use strict';
const operation = (f) => (l, r) => (...args) => f(l(...args), r(...args));
const unary = (f) => (val) => (...args) => f(val(...args));
const add = operation((l, r) => l + r);
const subtract = operation((l, r) => l - r);
const multiply = operation((l, r) => l * r);
const divide = operation((l, r) => l / r);
const square = unary((value) => Math.pow(value, 2));
const sqrt = unary((value) => Math.sqrt(Math.abs(value)));
const negate = unary((value) => (-1) * value);
const cnst = value => () => value;
const pi = cnst(Math.PI);
const e = cnst(Math.E);

const variable = value => (x, y, z) => {
    if (value === "x") {
        return x;
    }
    if (value === "y") {
        return y;
    }
    if (value === "z") {
        return z;
    }

// let expression = add(subtract(multiply(variable("x"), variable("x")), multiply(cnst(2), variable("x"))), cnst(1));

// for (let x = 0; x <= 10; x++) {
///    console.log(expression(x, 0, 0));
}