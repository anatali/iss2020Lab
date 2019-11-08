%====================================================================================
% radarusage description   
%====================================================================================
context(ctxradar, "192.168.1.8",  "TCP", "8038").
context(ctxradarusage, "localhost",  "TCP", "8044").
 qactor( radar, ctxradar, "external").
  qactor( demousage, ctxradarusage, "it.unibo.demousage.Demousage").
