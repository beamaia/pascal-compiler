program exArithmetics;

var
    a: integer;
    b, c: real;
    d: Array[1..10] of integer;
begin
    a := -1;
    b := 3.14;

    c:= 1 + 3.14;
    c:= 1 - 3.14;
    c:= 1 * 3.14;
    c:= 1 / 3.14;

    {c:= 'a' + 'b';}
    {c:= 'aaaa' - 'aaaab';}

    c := a + b;
    c := a * b;
    c := a / b;
    c := a - b;
    
    c := a - a;
    c := a + a;
    c := a * a;
    c := a / a;

    c := b + b;
    c := b * b;
    c := b / b;
    c := b - b;

    d[1] := 2;
    a := a div a;
    { c:= a + 'b';}


end.