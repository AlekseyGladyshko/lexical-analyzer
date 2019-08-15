# lexical-analyzer
Program that make lexical analysis to check if request is correct and to find out mistakes if there are any of them.
Lexical rules are set in a file as a LL(1) grammar (BNF). Rules are set for the SQL Update request, but you can set any of your own grammars.
Analyzer checks all rules in recursive way to find out any mistakes.

So in the cosnole you just put in the sentense and program will check it for any mistakes using the LL(1) grammar from the file.
