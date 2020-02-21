%====================================================================================
% syscoapdemo description   
%====================================================================================
context(ctxcoapdemo, "192.168.1.6",  "TCP", "8022").
 qactor( actor0, ctxcoapdemo, "it.unibo.actor0.Actor0").
  qactor( actor1, ctxcoapdemo, "it.unibo.actor1.Actor1").
