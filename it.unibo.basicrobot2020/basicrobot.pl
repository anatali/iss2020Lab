%====================================================================================
% basicrobot description   
%====================================================================================
context(ctxbasicrobot, "localhost",  "TCP", "8018").
 qactor( virtualrobotqa, ctxbasicrobot, "itunibo.robotVirtual.virtualRobotQa").
  qactor( basicrobot, ctxbasicrobot, "it.unibo.basicrobot.Basicrobot").
  qactor( robotcaller, ctxbasicrobot, "it.unibo.robotcaller.Robotcaller").
