/*
 * generated by Xtext 2.18.0.M3
 */
package it.unibo.xtextIntro20.services;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import java.util.List;
import org.eclipse.xtext.Alternatives;
import org.eclipse.xtext.Assignment;
import org.eclipse.xtext.Grammar;
import org.eclipse.xtext.GrammarUtil;
import org.eclipse.xtext.Group;
import org.eclipse.xtext.Keyword;
import org.eclipse.xtext.ParserRule;
import org.eclipse.xtext.RuleCall;
import org.eclipse.xtext.TerminalRule;
import org.eclipse.xtext.common.services.TerminalsGrammarAccess;
import org.eclipse.xtext.service.AbstractElementFinder.AbstractGrammarElementFinder;
import org.eclipse.xtext.service.GrammarProvider;

@Singleton
public class IotGrammarAccess extends AbstractGrammarElementFinder {
	
	public class IotSystemElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "it.unibo.xtextIntro20.Iot.IotSystem");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Keyword cSystemKeyword_0 = (Keyword)cGroup.eContents().get(0);
		private final Assignment cSpecAssignment_1 = (Assignment)cGroup.eContents().get(1);
		private final RuleCall cSpecIotSystemSpecParserRuleCall_1_0 = (RuleCall)cSpecAssignment_1.eContents().get(0);
		
		//IotSystem:
		//	"System" spec=IotSystemSpec;
		@Override public ParserRule getRule() { return rule; }
		
		//"System" spec=IotSystemSpec
		public Group getGroup() { return cGroup; }
		
		//"System"
		public Keyword getSystemKeyword_0() { return cSystemKeyword_0; }
		
		//spec=IotSystemSpec
		public Assignment getSpecAssignment_1() { return cSpecAssignment_1; }
		
		//IotSystemSpec
		public RuleCall getSpecIotSystemSpecParserRuleCall_1_0() { return cSpecIotSystemSpecParserRuleCall_1_0; }
	}
	public class IotSystemSpecElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "it.unibo.xtextIntro20.Iot.IotSystemSpec");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Assignment cNameAssignment_0 = (Assignment)cGroup.eContents().get(0);
		private final RuleCall cNameIDTerminalRuleCall_0_0 = (RuleCall)cNameAssignment_0.eContents().get(0);
		private final Assignment cMqttBrokerAssignment_1 = (Assignment)cGroup.eContents().get(1);
		private final RuleCall cMqttBrokerBrokerSpecParserRuleCall_1_0 = (RuleCall)cMqttBrokerAssignment_1.eContents().get(0);
		private final Assignment cMessageAssignment_2 = (Assignment)cGroup.eContents().get(2);
		private final RuleCall cMessageMessageParserRuleCall_2_0 = (RuleCall)cMessageAssignment_2.eContents().get(0);
		
		////terminal VARID  :  ('A'..'Z'|'_') ('a'..'z'|'A'..'Z'|'_'|'0'..'9')*;
		////QualifiedName : ID ('.' ID)* ;
		//IotSystemSpec:
		//	name=ID
		//	mqttBroker=BrokerSpec message+=Message* // N >=0
		//;
		@Override public ParserRule getRule() { return rule; }
		
		//name=ID mqttBroker=BrokerSpec message+=Message*
		public Group getGroup() { return cGroup; }
		
		//name=ID
		public Assignment getNameAssignment_0() { return cNameAssignment_0; }
		
		//ID
		public RuleCall getNameIDTerminalRuleCall_0_0() { return cNameIDTerminalRuleCall_0_0; }
		
		//mqttBroker=BrokerSpec
		public Assignment getMqttBrokerAssignment_1() { return cMqttBrokerAssignment_1; }
		
		//BrokerSpec
		public RuleCall getMqttBrokerBrokerSpecParserRuleCall_1_0() { return cMqttBrokerBrokerSpecParserRuleCall_1_0; }
		
		//// Optional
		//message+=Message*
		public Assignment getMessageAssignment_2() { return cMessageAssignment_2; }
		
		//Message
		public RuleCall getMessageMessageParserRuleCall_2_0() { return cMessageMessageParserRuleCall_2_0; }
	}
	public class BrokerSpecElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "it.unibo.xtextIntro20.Iot.BrokerSpec");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Keyword cMqttBrokerKeyword_0 = (Keyword)cGroup.eContents().get(0);
		private final Assignment cBrokerHostAssignment_1 = (Assignment)cGroup.eContents().get(1);
		private final RuleCall cBrokerHostSTRINGTerminalRuleCall_1_0 = (RuleCall)cBrokerHostAssignment_1.eContents().get(0);
		private final Keyword cColonKeyword_2 = (Keyword)cGroup.eContents().get(2);
		private final Assignment cBrokerPortAssignment_3 = (Assignment)cGroup.eContents().get(3);
		private final RuleCall cBrokerPortINTTerminalRuleCall_3_0 = (RuleCall)cBrokerPortAssignment_3.eContents().get(0);
		
		//BrokerSpec:
		//	"mqttBroker" brokerHost=STRING ":" brokerPort=INT;
		@Override public ParserRule getRule() { return rule; }
		
		//"mqttBroker" brokerHost=STRING ":" brokerPort=INT
		public Group getGroup() { return cGroup; }
		
		//"mqttBroker"
		public Keyword getMqttBrokerKeyword_0() { return cMqttBrokerKeyword_0; }
		
		//brokerHost=STRING
		public Assignment getBrokerHostAssignment_1() { return cBrokerHostAssignment_1; }
		
		//STRING
		public RuleCall getBrokerHostSTRINGTerminalRuleCall_1_0() { return cBrokerHostSTRINGTerminalRuleCall_1_0; }
		
		//":"
		public Keyword getColonKeyword_2() { return cColonKeyword_2; }
		
		//brokerPort=INT
		public Assignment getBrokerPortAssignment_3() { return cBrokerPortAssignment_3; }
		
		//INT
		public RuleCall getBrokerPortINTTerminalRuleCall_3_0() { return cBrokerPortINTTerminalRuleCall_3_0; }
	}
	public class MessageElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "it.unibo.xtextIntro20.Iot.Message");
		private final Alternatives cAlternatives = (Alternatives)rule.eContents().get(1);
		private final RuleCall cEventParserRuleCall_0 = (RuleCall)cAlternatives.eContents().get(0);
		private final RuleCall cDispatchParserRuleCall_1 = (RuleCall)cAlternatives.eContents().get(1);
		private final RuleCall cRequestParserRuleCall_2 = (RuleCall)cAlternatives.eContents().get(2);
		
		//Message:
		//	Event | Dispatch | Request;
		@Override public ParserRule getRule() { return rule; }
		
		//Event | Dispatch | Request
		public Alternatives getAlternatives() { return cAlternatives; }
		
		//Event
		public RuleCall getEventParserRuleCall_0() { return cEventParserRuleCall_0; }
		
		//Dispatch
		public RuleCall getDispatchParserRuleCall_1() { return cDispatchParserRuleCall_1; }
		
		//Request
		public RuleCall getRequestParserRuleCall_2() { return cRequestParserRuleCall_2; }
	}
	public class EventElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "it.unibo.xtextIntro20.Iot.Event");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Keyword cEventKeyword_0 = (Keyword)cGroup.eContents().get(0);
		private final Assignment cNameAssignment_1 = (Assignment)cGroup.eContents().get(1);
		private final RuleCall cNameIDTerminalRuleCall_1_0 = (RuleCall)cNameAssignment_1.eContents().get(0);
		private final Keyword cColonKeyword_2 = (Keyword)cGroup.eContents().get(2);
		private final Assignment cMsgAssignment_3 = (Assignment)cGroup.eContents().get(3);
		private final RuleCall cMsgSTRINGTerminalRuleCall_3_0 = (RuleCall)cMsgAssignment_3.eContents().get(0);
		
		//Event:
		//	"Event" name=ID ":" msg=STRING;
		@Override public ParserRule getRule() { return rule; }
		
		//"Event" name=ID ":" msg=STRING
		public Group getGroup() { return cGroup; }
		
		//"Event"
		public Keyword getEventKeyword_0() { return cEventKeyword_0; }
		
		//name=ID
		public Assignment getNameAssignment_1() { return cNameAssignment_1; }
		
		//ID
		public RuleCall getNameIDTerminalRuleCall_1_0() { return cNameIDTerminalRuleCall_1_0; }
		
		//":"
		public Keyword getColonKeyword_2() { return cColonKeyword_2; }
		
		//msg=STRING
		public Assignment getMsgAssignment_3() { return cMsgAssignment_3; }
		
		//STRING
		public RuleCall getMsgSTRINGTerminalRuleCall_3_0() { return cMsgSTRINGTerminalRuleCall_3_0; }
	}
	public class DispatchElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "it.unibo.xtextIntro20.Iot.Dispatch");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Keyword cDispatchKeyword_0 = (Keyword)cGroup.eContents().get(0);
		private final Assignment cNameAssignment_1 = (Assignment)cGroup.eContents().get(1);
		private final RuleCall cNameIDTerminalRuleCall_1_0 = (RuleCall)cNameAssignment_1.eContents().get(0);
		private final Keyword cColonKeyword_2 = (Keyword)cGroup.eContents().get(2);
		private final Assignment cMsgAssignment_3 = (Assignment)cGroup.eContents().get(3);
		private final RuleCall cMsgSTRINGTerminalRuleCall_3_0 = (RuleCall)cMsgAssignment_3.eContents().get(0);
		
		//Dispatch:
		//	"Dispatch" name=ID ":" msg=STRING;
		@Override public ParserRule getRule() { return rule; }
		
		//"Dispatch" name=ID ":" msg=STRING
		public Group getGroup() { return cGroup; }
		
		//"Dispatch"
		public Keyword getDispatchKeyword_0() { return cDispatchKeyword_0; }
		
		//name=ID
		public Assignment getNameAssignment_1() { return cNameAssignment_1; }
		
		//ID
		public RuleCall getNameIDTerminalRuleCall_1_0() { return cNameIDTerminalRuleCall_1_0; }
		
		//":"
		public Keyword getColonKeyword_2() { return cColonKeyword_2; }
		
		//msg=STRING
		public Assignment getMsgAssignment_3() { return cMsgAssignment_3; }
		
		//STRING
		public RuleCall getMsgSTRINGTerminalRuleCall_3_0() { return cMsgSTRINGTerminalRuleCall_3_0; }
	}
	public class RequestElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "it.unibo.xtextIntro20.Iot.Request");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Keyword cRequestKeyword_0 = (Keyword)cGroup.eContents().get(0);
		private final Assignment cNameAssignment_1 = (Assignment)cGroup.eContents().get(1);
		private final RuleCall cNameIDTerminalRuleCall_1_0 = (RuleCall)cNameAssignment_1.eContents().get(0);
		private final Keyword cColonKeyword_2 = (Keyword)cGroup.eContents().get(2);
		private final Assignment cMsgAssignment_3 = (Assignment)cGroup.eContents().get(3);
		private final RuleCall cMsgSTRINGTerminalRuleCall_3_0 = (RuleCall)cMsgAssignment_3.eContents().get(0);
		
		//Request:
		//	"Request" name=ID ":" msg=STRING;
		@Override public ParserRule getRule() { return rule; }
		
		//"Request" name=ID ":" msg=STRING
		public Group getGroup() { return cGroup; }
		
		//"Request"
		public Keyword getRequestKeyword_0() { return cRequestKeyword_0; }
		
		//name=ID
		public Assignment getNameAssignment_1() { return cNameAssignment_1; }
		
		//ID
		public RuleCall getNameIDTerminalRuleCall_1_0() { return cNameIDTerminalRuleCall_1_0; }
		
		//":"
		public Keyword getColonKeyword_2() { return cColonKeyword_2; }
		
		//msg=STRING
		public Assignment getMsgAssignment_3() { return cMsgAssignment_3; }
		
		//STRING
		public RuleCall getMsgSTRINGTerminalRuleCall_3_0() { return cMsgSTRINGTerminalRuleCall_3_0; }
	}
	
	
	private final IotSystemElements pIotSystem;
	private final IotSystemSpecElements pIotSystemSpec;
	private final BrokerSpecElements pBrokerSpec;
	private final MessageElements pMessage;
	private final EventElements pEvent;
	private final DispatchElements pDispatch;
	private final RequestElements pRequest;
	
	private final Grammar grammar;
	
	private final TerminalsGrammarAccess gaTerminals;

	@Inject
	public IotGrammarAccess(GrammarProvider grammarProvider,
			TerminalsGrammarAccess gaTerminals) {
		this.grammar = internalFindGrammar(grammarProvider);
		this.gaTerminals = gaTerminals;
		this.pIotSystem = new IotSystemElements();
		this.pIotSystemSpec = new IotSystemSpecElements();
		this.pBrokerSpec = new BrokerSpecElements();
		this.pMessage = new MessageElements();
		this.pEvent = new EventElements();
		this.pDispatch = new DispatchElements();
		this.pRequest = new RequestElements();
	}
	
	protected Grammar internalFindGrammar(GrammarProvider grammarProvider) {
		Grammar grammar = grammarProvider.getGrammar(this);
		while (grammar != null) {
			if ("it.unibo.xtextIntro20.Iot".equals(grammar.getName())) {
				return grammar;
			}
			List<Grammar> grammars = grammar.getUsedGrammars();
			if (!grammars.isEmpty()) {
				grammar = grammars.iterator().next();
			} else {
				return null;
			}
		}
		return grammar;
	}
	
	@Override
	public Grammar getGrammar() {
		return grammar;
	}
	
	
	public TerminalsGrammarAccess getTerminalsGrammarAccess() {
		return gaTerminals;
	}

	
	//IotSystem:
	//	"System" spec=IotSystemSpec;
	public IotSystemElements getIotSystemAccess() {
		return pIotSystem;
	}
	
	public ParserRule getIotSystemRule() {
		return getIotSystemAccess().getRule();
	}
	
	////terminal VARID  :  ('A'..'Z'|'_') ('a'..'z'|'A'..'Z'|'_'|'0'..'9')*;
	////QualifiedName : ID ('.' ID)* ;
	//IotSystemSpec:
	//	name=ID
	//	mqttBroker=BrokerSpec message+=Message* // N >=0
	//;
	public IotSystemSpecElements getIotSystemSpecAccess() {
		return pIotSystemSpec;
	}
	
	public ParserRule getIotSystemSpecRule() {
		return getIotSystemSpecAccess().getRule();
	}
	
	//BrokerSpec:
	//	"mqttBroker" brokerHost=STRING ":" brokerPort=INT;
	public BrokerSpecElements getBrokerSpecAccess() {
		return pBrokerSpec;
	}
	
	public ParserRule getBrokerSpecRule() {
		return getBrokerSpecAccess().getRule();
	}
	
	//Message:
	//	Event | Dispatch | Request;
	public MessageElements getMessageAccess() {
		return pMessage;
	}
	
	public ParserRule getMessageRule() {
		return getMessageAccess().getRule();
	}
	
	//Event:
	//	"Event" name=ID ":" msg=STRING;
	public EventElements getEventAccess() {
		return pEvent;
	}
	
	public ParserRule getEventRule() {
		return getEventAccess().getRule();
	}
	
	//Dispatch:
	//	"Dispatch" name=ID ":" msg=STRING;
	public DispatchElements getDispatchAccess() {
		return pDispatch;
	}
	
	public ParserRule getDispatchRule() {
		return getDispatchAccess().getRule();
	}
	
	//Request:
	//	"Request" name=ID ":" msg=STRING;
	public RequestElements getRequestAccess() {
		return pRequest;
	}
	
	public ParserRule getRequestRule() {
		return getRequestAccess().getRule();
	}
	
	//terminal ID:
	//	'^'? ('a'..'z' | 'A'..'Z' | '_') ('a'..'z' | 'A'..'Z' | '_' | '0'..'9')*;
	public TerminalRule getIDRule() {
		return gaTerminals.getIDRule();
	}
	
	//terminal INT returns ecore::EInt:
	//	'0'..'9'+;
	public TerminalRule getINTRule() {
		return gaTerminals.getINTRule();
	}
	
	//terminal STRING:
	//	'"' ('\\' . | !('\\' | '"'))* '"' | "'" ('\\' . | !('\\' | "'"))* "'";
	public TerminalRule getSTRINGRule() {
		return gaTerminals.getSTRINGRule();
	}
	
	//terminal ML_COMMENT:
	//	'/*'->'*/';
	public TerminalRule getML_COMMENTRule() {
		return gaTerminals.getML_COMMENTRule();
	}
	
	//terminal SL_COMMENT:
	//	'//' !('\n' | '\r')* ('\r'? '\n')?;
	public TerminalRule getSL_COMMENTRule() {
		return gaTerminals.getSL_COMMENTRule();
	}
	
	//terminal WS:
	//	' ' | '\t' | '\r' | '\n'+;
	public TerminalRule getWSRule() {
		return gaTerminals.getWSRule();
	}
	
	//terminal ANY_OTHER:
	//	.;
	public TerminalRule getANY_OTHERRule() {
		return gaTerminals.getANY_OTHERRule();
	}
}
