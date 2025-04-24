prime(1):-!.
prime(2).
prime_divisors(1, []) :- !.
prime_divisors(1, [], _) :- !.
factor(2, 3) :- !.
nth_prime(1, 2) :- !.

prime(N) :-
    N > 1,
    \+ any_divs(N, 2).
any_divs(N, Divisor) :-
    N mod Divisor =:= 0.
any_divs(N, Divisor) :-
    Divisor * Divisor < N,
    any_divs(N, Divisor + 1).

composite(N) :-
    \+ prime(N).

prime_divisors(N, Divisors) :-
    prime_divisors(N, Divisors, 2).
prime_divisors(N, [F | Divisors], F) :-
    N mod F =:= 0,
    N1 is N // F,
    prime_divisors(N1, Divisors, F), !.
prime_divisors(N, Divisors, F) :-
    F * F < N,
    factor(F, F1),
    prime_divisors(N, Divisors, F1), !.
prime_divisors(N, [N], _) :-
    prime(N).
factor(F, F1) :-
    F1 is F + 2.

nth_prime(N, P) :-
    nth_prime(1, N, 3, P).
nth_prime(Count, Final, Current, P) :-
    prime(Current),
    New is Count + 1,
    nth_prime_continue(New, Final, Current, P).
nth_prime(Count, Final, Current, P) :-
    composite(Current),
    Next is Current + 2,
    nth_prime(Count, Final, Next, P).
nth_prime_continue(Final, Final, Current, Current) :- !.
nth_prime_continue(Count, Useful, Current, P) :-
    Next is Current + 2,
    nth_prime(Count, Useful, Next, P).
