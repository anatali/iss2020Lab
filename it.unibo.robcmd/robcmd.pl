%====================================================================================
% robcmd description   
%====================================================================================
context(ctxrobcmd, "localhost",  "TCP", "8020" ).
 qactor( console, ctxrobcmd, "it.unibo.console.Console").
  qactor( robot, ctxrobcmd, "it.unibo.robot.Robot").
