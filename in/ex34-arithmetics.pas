program exOplogicErr;

var
    a: integer;
    b, c: real;
begin
    a := 1;
    b := 3.14;

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

    if a + b then
        writeln('Number 1 is bigger');
end.