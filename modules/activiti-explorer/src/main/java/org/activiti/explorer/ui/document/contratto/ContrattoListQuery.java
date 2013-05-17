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

package org.activiti.explorer.ui.document.contratto;

import java.util.ArrayList;
import java.util.List;

import org.activiti.engine.ContrattoService;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.document.contratto.Contratto;
import org.activiti.explorer.data.AbstractLazyLoadingQuery;

import com.vaadin.data.Item;
import com.vaadin.data.util.ObjectProperty;
import com.vaadin.data.util.PropertysetItem;


/**
 * @author Joram Barrez
 */
public class ContrattoListQuery extends AbstractLazyLoadingQuery {
  
  protected transient ContrattoService contrattoService;
  
  
  public ContrattoListQuery() {
    this.contrattoService = ProcessEngines.getDefaultProcessEngine().getContrattoService();
  }

  public int size() {
    return (int) contrattoService.createContrattoQuery().count();
  }

  public List<Item> loadItems(int start, int count) {
    List<Contratto> contratti = contrattoService.createContrattoQuery()
      .orderByContrattoId().asc()
      .listPage(start, count);
    
    List<Item> contrattoListItems = new ArrayList<Item>();
    for (Contratto contratto : contratti) {
      contrattoListItems.add(new ContrattoListItem(contratto));
    }
    return contrattoListItems;
  }

  public Item loadSingleResult(String id) {
    return new ContrattoListItem(contrattoService.createContrattoQuery().contrattoId(id).singleResult());
  }

  public void setSorting(Object[] propertyIds, boolean[] ascending) {
    throw new UnsupportedOperationException();
  }
  
  class ContrattoListItem extends PropertysetItem implements Comparable<ContrattoListItem> {
    
    private static final long serialVersionUID = 1L;

    public ContrattoListItem(Contratto contratto) {
      addItemProperty("id", new ObjectProperty<String>(contratto.getId(), String.class));
      addItemProperty("name", new ObjectProperty<String>("Contratto " + contratto.getId(), String.class));
    }

    public int compareTo(ContrattoListItem other) {
        String id = (String) getItemProperty("id").getValue();
        String otherId = (String) other.getItemProperty("id").getValue();
        return id.compareTo(otherId);
    }
    
  }

}
