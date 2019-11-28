%====================================================================================
% robotradarsys description   
%====================================================================================
context(ctxradargui, "localhost",  "TCP", "8038").
 qactor( radargui, ctxradargui, "it.unibo.radargui.Radargui").
  qactor( sonarsimulator, ctxradargui, "it.unibo.sonarsimulator.Sonarsimulator").
