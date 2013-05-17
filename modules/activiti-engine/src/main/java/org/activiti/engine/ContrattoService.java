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
package org.activiti.engine;

import java.util.List;

import org.activiti.engine.document.contratto.Contratto;
import org.activiti.engine.document.contratto.ContrattoQuery;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.GroupQuery;
import org.activiti.engine.identity.Picture;
import org.activiti.engine.identity.User;
import org.activiti.engine.identity.UserQuery;


/**
 * Service to manage {@link Contratto}s 
 * 
 * @author Tom Baeyens
 */
public interface ContrattoService {

  /**
   * Creates a new Contratto. The Contratto is transient and must be saved using 
   * {@link #saveContratto(Contratto)}.
   * @param contrattoId id for the new contratto, cannot be null.
   */
  Contratto newContratto(String contrattoId);
  
  /**
   * Saves the Contratto. If the Contratto already existed, the Contratto is updated.
   * @param contratto contratto to save, cannot be null.
   * @throws RuntimeException when a Contratto with the same name already exists.
   */
  void saveContratto(Contratto contratto);
  
  /**
   * Creates a {@link ContrattoQuery} that allows to programmatically query the Contratti.
   */
  ContrattoQuery createContrattoQuery();
  
  /**
   * @param contrattoId id of contratto to delete, cannot be null. When an id is passed
   * for an unexisting Contratto, this operation is ignored.
   */
  void deleteContratto(String contrattoId);
  
 
}
