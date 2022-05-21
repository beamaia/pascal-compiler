program ifElse;
var 
    subTwo,xOne,xTwo:integer;
    xOneBig,xTwoBig:boolean;

begin
    subTwo := xOne - xTwo;

    (* If com begins e ends *)
    if subTwo > 0 then
        begin
            xOneBig:=1;
            xTwoBig:=0;
        end;
    else if (subTwo < 0) then
        begin   
            xOneBig:=0;
            xTwoBig:=1;
        end;
    else
        begin 
            xOneBig:=0;
            xTwoBig:=0;
        end;
    
    (* If sem begin e end *)
    if xOneBig then
        writeln('Number 1 is bigger');
    else if xTwoBig then
        writeln('Number 2 is bigger');

    (* If sem else *)
    if xOneBig=0 and xTwoBig=0 then
        writeln('They are equal');

    (* If sem else *)
    if xOneBig=xTwoBig then
        begin
            writeln('They are equal');
        end;
        
    
    (* If/else aninhados*)
    if xOneBig=0 and xTwoBig=0 then
        writeln('They are equal');
        
        (* If aninhado sem begin/end *)
        if xOne div 2 = 1 then
            writeln('Number 1 is odd');
        else 
            writln('Number 1 is even');

        (* If aninhado com begin/end *)
        if xTwo div 2 = 1 then
            begin
                writeln('Number 2 is odd');
            end;
        else
            begin 
                writln('Number 2 is even');
            end;
end.