%====================================================================================
% basicrobot description   
%====================================================================================
context(ctxbasicrobot, "localhost",  "TCP", "8018").
 qactor( robotadapter, ctxbasicrobot, "itunibo.robot.robotAdapterQaWithSonarDataFilter").
  qactor( basicrobot, ctxbasicrobot, "it.unibo.basicrobot.Basicrobot").
  qactor( sentinel, ctxbasicrobot, "it.unibo.sentinel.Sentinel").
tracing.
