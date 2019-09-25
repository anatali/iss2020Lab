%====================================================================================
% robcmd description   
%====================================================================================
context(ctxrobot, "localhost",  "TCP", "8020" ).
context(ctxconsole, "192.168.1.7",  "TCP", "8030" ).
 qactor( console, ctxconsole, "it.unibo.console.Console").
  qactor( robot, ctxrobot, "it.unibo.robot.Robot").
