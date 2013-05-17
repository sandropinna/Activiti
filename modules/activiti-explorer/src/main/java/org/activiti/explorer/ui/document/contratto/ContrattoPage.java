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

import org.activiti.explorer.ExplorerApp;
import org.activiti.explorer.data.LazyLoadingContainer;
import org.activiti.explorer.data.LazyLoadingQuery;
import org.activiti.explorer.navigation.ContrattoNavigator;
import org.activiti.explorer.navigation.UriFragment;
import org.activiti.explorer.ui.Images;
import org.activiti.explorer.ui.document.DocumentPage;
import org.activiti.explorer.ui.util.ThemeImageColumnGenerator;

import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.ui.Table;


/**
 * Page for managing contratti.
 * 
 * @author Joram Barrez
 */
public class ContrattoPage extends DocumentPage {

  private static final long serialVersionUID = 1L;
  protected String contrattoId;
  protected Table contrattoTable;
  protected LazyLoadingQuery contrattoListQuery;
  protected LazyLoadingContainer contrattoListContainer;
  
  public ContrattoPage() {
    ExplorerApp.get().setCurrentUriFragment(
            new UriFragment(ContrattoNavigator.CONTRATTO_URI_PART));
  }
  
  public ContrattoPage(String contrattoId) {
    this.contrattoId = contrattoId;
  }
  
  @Override
  protected void initUi() {
    super.initUi();
    
    if (contrattoId == null) {
      selectElement(0);
    } else {
      selectElement(contrattoListContainer.getIndexForObjectId(contrattoId));
    }
  }

  protected Table createList() {
    contrattoTable = new Table();
    
    contrattoListQuery = new ContrattoListQuery();
    contrattoListContainer = new LazyLoadingContainer(contrattoListQuery, 30);
    contrattoTable.setContainerDataSource(contrattoListContainer);
    
    // Column headers
   // contrattoTable.addGeneratedColumn("icon", new ThemeImageColumnGenerator(Images.USER_22));
   // contrattoTable.setColumnWidth("icon", 22);
    contrattoTable.addContainerProperty("name", String.class, null);
    contrattoTable.setColumnHeaderMode(Table.COLUMN_HEADER_MODE_HIDDEN);
            
    // Listener to change right panel when clicked on a Contratto
    contrattoTable.addListener(new Property.ValueChangeListener() {
      private static final long serialVersionUID = 1L;
      public void valueChange(ValueChangeEvent event) {
        Item item = contrattoTable.getItem(event.getProperty().getValue()); // the value of the property is the itemId of the table entry
        if(item != null) {
          String contrattoId = (String) item.getItemProperty("id").getValue();
          setDetailComponent(new ContrattoDetailPanel(ContrattoPage.this, contrattoId));
          
          // Update URL
          ExplorerApp.get().setCurrentUriFragment(
            new UriFragment(ContrattoNavigator.CONTRATTO_URI_PART, contrattoId));
        } else {
          // Nothing is selected
          setDetailComponent(null);
          ExplorerApp.get().setCurrentUriFragment(new UriFragment(ContrattoNavigator.CONTRATTO_URI_PART));
        }
      }
    });
    
    return contrattoTable;
  }
  
  /**
   * Call when some Contratto data has been changed
   */
  public void notifyContrattoChanged(String contrattoId) {
    // Clear cache
    contrattoTable.removeAllItems();
    contrattoListContainer.removeAllItems();
    
    contrattoTable.select(contrattoListContainer.getIndexForObjectId(contrattoId));
  }
  
}
