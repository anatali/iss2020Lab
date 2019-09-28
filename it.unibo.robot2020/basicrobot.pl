%====================================================================================
% basicrobot description   
%====================================================================================
context(ctxrobot, "localhost",  "TCP", "8020" ).
 qactor( robot, ctxrobot, "it.unibo.robot.Robot").
  qactor( sonarhandler, ctxrobot, "it.unibo.sonarhandler.Sonarhandler").
