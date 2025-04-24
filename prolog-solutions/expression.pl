:- load_library('alice.tuprolog.lib.DCGLibrary').

lookup(K, [(K, V) | _], V).
lookup(K, [_ | T], V) :- lookup(K, T, V).

const(Value, const(Value)).
variable(Name, variable(Name)).

operation(op_add, A, B, R) :- R is A + B.
operation(op_subtract, A, B, R) :- R is A - B.
operation(op_multiply, A, B, R) :- R is A * B.
operation(op_divide, A, B, R) :- R is A / B.
operation(op_negate, A, R) :- R is -A.
operation(op_square, A, R) :- R is A * A.
operation(op_sqrt, A, R) :- R is sqrt(A).

evaluate(const(Value), _, Value).
evaluate(variable(Name), Vars, R) :- atom_chars(Name, [Var | _]), lookup(Var, Vars, R).
evaluate(operation(Op, A), Vars, R) :-
    evaluate(A, Vars, AV),
    operation(Op, AV, R).
evaluate(operation(Op, A, B), Vars, R) :-
    evaluate(A, Vars, AV),
    evaluate(B, Vars, BV),
    operation(Op, AV, BV, R).

nonvar(V, _) :- var(V).
nonvar(V, T) :- nonvar(V), call(T).

expr_p(variable(Var)) -->
    { nonvar(Var, atom_chars(Var, Chars)) },
    skip_whitespace, var_p(Chars), skip_whitespace,
    { \+ Chars = ['-'], Chars = [_ | _], atom_chars(Var, Chars) }.
var_p([]) --> [].
var_p([H | T]) -->
    { member(H, ['x', 'y', 'z']) },
    [H],
    var_p(T).

expr_p(const(Value)) -->
    { nonvar(Value, number_chars(Value, Chars)) },
    skip_whitespace, digits_p(Chars), skip_whitespace,
    { \+ Chars = ['-'], Chars = [_ | _], number_chars(Value, Chars) }.
digits_p([]) --> [].
digits_p([H | T]) -->
    { member(H, ['0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '.', '-'])},
    [H],
    digits_p(T).

op_p(op_add) --> skip_whitespace, ['+'], skip_whitespace.
op_p(op_subtract) --> skip_whitespace, ['-'], skip_whitespace.
op_p(op_multiply) --> skip_whitespace, ['*'], skip_whitespace.
op_p(op_divide) --> skip_whitespace, ['/'], skip_whitespace.
op_p(op_negate) --> skip_whitespace, ['n'], ['e'], ['g'], ['a'], ['t'], ['e'], skip_whitespace.
op_p(op_square) --> skip_whitespace, ['s'], ['q'], ['u'], ['a'], ['r'], ['e'], skip_whitespace.
op_p(op_sqrt) --> skip_whitespace, ['s'], ['q'], ['r'], ['t'], skip_whitespace.

expr_p(operation(Op, A)) --> skip_whitespace, ['('], op_p(Op), [' '], expr_p(A), [')'], skip_whitespace.
expr_p(operation(Op, A, B)) --> skip_whitespace, ['('], op_p(Op), [' '], expr_p(A), [' '], expr_p(B), [')'], skip_whitespace.

prefix_str(E, A) :- ground(E), phrase(expr_p(E), C), atom_chars(A, C), !.
prefix_str(E, A) :- atom(A), atom_chars(A, C), phrase(expr_p(E), C), !.

skip_whitespace --> [].
skip_whitespace --> [' '], skip_whitespace.
