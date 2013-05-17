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

package org.activiti.engine.impl.persistence.entity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.document.contratto.Contratto;
import org.activiti.engine.document.contratto.ContrattoQuery;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.activiti.engine.identity.UserQuery;
import org.activiti.engine.impl.ContrattoQueryImpl;
import org.activiti.engine.impl.Page;
import org.activiti.engine.impl.UserQueryImpl;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.db.DbSqlSession;
import org.activiti.engine.impl.db.PersistentObject;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.AbstractManager;



public class ContrattoEntityManager extends AbstractManager {

  public Contratto createNewContratto(String contrattoId) {
    return new ContrattoEntity(contrattoId);
  }

  public void insertContratto(Contratto contratto) {
    getDbSqlSession().insert((PersistentObject) contratto);
  }
  
  public void updateContratto(ContrattoEntity updatedContratto) {
    CommandContext commandContext = Context.getCommandContext();
    DbSqlSession dbSqlSession = commandContext.getDbSqlSession();
    dbSqlSession.update(updatedContratto);
  }

  public ContrattoEntity findContrattoById(String contrattoId) {
    return (ContrattoEntity) getDbSqlSession().selectOne("selectContrattoById", contrattoId);
  }

  @SuppressWarnings("unchecked")
  public void deleteContratto(String contrattoId) {
    ContrattoEntity contratto = findContrattoById(contrattoId);
    if (contratto!=null) {
      getDbSqlSession().delete(contratto);
    }
  }
  
  @SuppressWarnings("unchecked")
  public List<Contratto> findContrattoByQueryCriteria(ContrattoQueryImpl query, Page page) {
    return getDbSqlSession().selectList("selectContrattoByQueryCriteria", query, page);
  }
  
  public long findContrattoCountByQueryCriteria(ContrattoQueryImpl query) {
    return (Long) getDbSqlSession().selectOne("selectContrattoCountByQueryCriteria", query);
  }
  
  
  public ContrattoQuery createNewContrattoQuery() {
    return new ContrattoQueryImpl(Context.getProcessEngineConfiguration().getCommandExecutorTxRequired());
  }  
  
}
