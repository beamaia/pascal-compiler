program ifElse;
var 
    subTwo,xOne,xTwo:integer;
    xOneBig,xTwoBig:boolean;

begin

    (* If com begins e ends *)
    if subTwo > 0 then
        begin
            xOneBig:=true;
            xTwoBig:=false;
        end;
    
    else
        begin 
            xOneBig:=false;
            xTwoBig:=false;
        end;
    
    (* If sem begin e end *)
    {if xOneBig then
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
        
        (* If aninhado sem begin/end *)}
        if (xOne * 2) = 1 then
            xOne := 30;
        else 
            xTwo := -30;

        (* If aninhado com begin/end *)
        {if xTwo div 2 = 1 then
            begin
                writeln('Number 2 is odd');
            end;
        else
            begin 
                writln('Number 2 is even');
            end;}
end.