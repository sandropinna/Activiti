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

import org.activiti.engine.ContrattoService;
import org.activiti.engine.document.contratto.Contratto;
import org.activiti.engine.document.contratto.ContrattoQuery;
import org.activiti.engine.impl.cmd.CreateContrattoCmd;
import org.activiti.engine.impl.cmd.CreateContrattoQueryCmd;
import org.activiti.engine.impl.cmd.DeleteContrattoCmd;
import org.activiti.engine.impl.cmd.SaveContrattoCmd;


public class ContrattoServiceImpl extends ServiceImpl implements ContrattoService {		
	
  @Override
	public Contratto newContratto(String contrattoId) {
		
		return commandExecutor.execute(new CreateContrattoCmd(contrattoId));
	}

	@Override
	public void saveContratto(Contratto contratto) {
		commandExecutor.execute(new SaveContrattoCmd(contratto));
		
	}

	@Override
	public ContrattoQuery createContrattoQuery() {
		return commandExecutor.execute(new CreateContrattoQueryCmd());
	}

	@Override
	public void deleteContratto(String contrattoId) {
		commandExecutor.execute(new DeleteContrattoCmd(contrattoId));		
	}
	
}
