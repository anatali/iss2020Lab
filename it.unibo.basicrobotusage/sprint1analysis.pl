%====================================================================================
% sprint1analysis description   
%====================================================================================
context(ctxbasicrobot, "192.168.43.171",  "TCP", "8018").
context(ctxrobotappl0, "localhost",  "TCP", "8033").
context(ctxradargui, "192.168.43.5",  "TCP", "8038").
 qactor( basicrobot, ctxbasicrobot, "external").
  qactor( radargui, ctxradargui, "external").
  qactor( robotappl0, ctxrobotappl0, "it.unibo.robotappl0.Robotappl0").
