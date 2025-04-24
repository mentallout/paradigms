map_build([], nil).
map_build([(Key, Value) | Tail], TreeMap) :-
    map_build(Tail, ListMap),
    insert(Key, Value, ListMap, TreeMap).

insert(Key, Value, nil, t(Key, Value, nil, nil)).
insert(Key, Value, t(Key, _, Left, Right), t(Key, Value, Left, Right)).
insert(Key, Value, t(Key1, Value1, Left, Right), t(Key1, Value1, Left, NewRight)) :-
    Key > Key1,
    insert(Key, Value, Right, NewRight).
insert(Key, Value, t(Key1, Value1, Left, Right), t(Key1, Value1, NewLeft, Right)) :-
    Key < Key1,
    insert(Key, Value, Left, NewLeft).

map_get(t(Key, Value, _, _), Key, Value).
map_get(t(Key1, _, _, Right), Key, Value) :-
    Key > Key1,
    map_get(Right, Key, Value).
map_get(t(Key1, _, Left, _), Key, Value) :-
    Key < Key1,
    map_get(Left, Key, Value).

map_lastKey(t(Key, _, _, nil), Key).
map_lastKey(t(_, _, _, Right), Key) :-
    map_lastKey(Right, Key).

map_lastValue(t(_, Value, _, nil), Value).
map_lastValue(t(_, _, _, Right), Value) :-
    map_lastValue(Right, Value).

map_lastEntry(Map, (Key, Value)) :-
    map_lastKey(Map, Key),
    map_get(Map, Key, Value).