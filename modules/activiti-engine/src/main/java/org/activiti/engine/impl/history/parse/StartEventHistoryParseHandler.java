/* Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.activiti.engine.impl.history.parse;

import org.activiti.bpmn.model.BaseElement;
import org.activiti.bpmn.model.StartEvent;
import org.activiti.bpmn.model.SubProcess;
import org.activiti.engine.impl.bpmn.parser.BpmnParse;
import org.activiti.engine.impl.bpmn.parser.handler.AbstractSingleElementBpmnParseHandler;
import org.activiti.engine.impl.history.handler.StartEventEndHandler;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.impl.pvm.process.ScopeImpl;


/**
 * @author Joram Barrez
 */
public class StartEventHistoryParseHandler extends AbstractSingleElementBpmnParseHandler<StartEvent> {
  
  protected static final StartEventEndHandler START_EVENT_END_HANDLER = new StartEventEndHandler();

  protected Class< ? extends BaseElement> getHandledType() {
    return StartEvent.class;
  }
  
  protected void executeParse(BpmnParse bpmnParse, StartEvent element, ScopeImpl scope, ActivityImpl activity, SubProcess subProcess) {
    bpmnParse.getCurrentActivity().addExecutionListener(org.activiti.engine.impl.pvm.PvmEvent.EVENTNAME_END, START_EVENT_END_HANDLER);
  }

}