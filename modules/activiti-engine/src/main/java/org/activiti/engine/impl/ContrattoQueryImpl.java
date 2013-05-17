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

package org.activiti.engine.impl;

import java.util.List;

import org.activiti.engine.ActivitiIllegalArgumentException;
import org.activiti.engine.document.contratto.Contratto;
import org.activiti.engine.document.contratto.ContrattoQuery;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.interceptor.CommandExecutor;


/**
 * @author Joram Barrez
 */
public class ContrattoQueryImpl extends AbstractQuery<ContrattoQuery, Contratto> implements ContrattoQuery {
  
  private static final long serialVersionUID = 1L;
  protected String id;
 
  
  public ContrattoQueryImpl() {
  }

  public ContrattoQueryImpl(CommandContext commandContext) {
    super(commandContext);
  }

  public ContrattoQueryImpl(CommandExecutor commandExecutor) {
    super(commandExecutor);
  }

  public ContrattoQuery contrattoId(String id) {
    if (id == null) {
      throw new ActivitiIllegalArgumentException("Provided id is null");
    }
    this.id = id;
    return this;
  }
  
 

  //sorting //////////////////////////////////////////////////////////
  
  public ContrattoQuery orderByContrattoId() {
    return orderBy(ContrattoQueryProperty.CONTRATTO_ID);
  }
  
   //results //////////////////////////////////////////////////////////
  
  public long executeCount(CommandContext commandContext) {
    checkQueryOk();
    return commandContext
      .getContrattoEntityManager()
      .findContrattoCountByQueryCriteria(this);
  }
  
  public List<Contratto> executeList(CommandContext commandContext, Page page) {
    checkQueryOk();
    return commandContext
      .getContrattoEntityManager()
      .findContrattoByQueryCriteria(this, page);
  }
  
  //getters //////////////////////////////////////////////////////////

  public String getId() {
    return id;
  }
 
}
