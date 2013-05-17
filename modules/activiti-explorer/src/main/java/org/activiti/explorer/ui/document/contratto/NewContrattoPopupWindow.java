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

import org.activiti.engine.ContrattoService;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.document.contratto.Contratto;
import org.activiti.explorer.ExplorerApp;
import org.activiti.explorer.I18nManager;
import org.activiti.explorer.ui.custom.PopupWindow;

import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.event.Action;
import com.vaadin.event.Action.Handler;
import com.vaadin.event.ShortcutAction;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Form;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.Reindeer;


/**
 * Popup window to create a new contratto.
 * 
 * @author Joram Barrez
 */
public class NewContrattoPopupWindow extends PopupWindow {
  
  private static final long serialVersionUID = 1L;
  protected transient ContrattoService contrattoService;
  protected I18nManager i18nManager;
  protected Form form;
  
  public NewContrattoPopupWindow() {
    this.contrattoService = ProcessEngines.getDefaultProcessEngine().getContrattoService();
    this.i18nManager = ExplorerApp.get().getI18nManager();
    
    setCaption("Crea Contratto");
    setModal(true);
    center();
    setResizable(false);
    setWidth(275, UNITS_PIXELS);
    setHeight(300, UNITS_PIXELS);
    addStyleName(Reindeer.WINDOW_LIGHT);
    
    initEnterKeyListener();
    initForm();
  }
  
  protected void initEnterKeyListener() {
    addActionHandler(new Handler() {
      public void handleAction(Action action, Object sender, Object target) {
        handleFormSubmit();
      }
      public Action[] getActions(Object target, Object sender) {
        return new Action[] {new ShortcutAction("enter", ShortcutAction.KeyCode.ENTER, null)};
      }
    });
  }
  
  protected void initForm() {
    form = new Form();
    form.setValidationVisibleOnCommit(true);
    form.setImmediate(true);
    addComponent(form);
    
    initInputFields();
    initCreateButton();
  }

  protected void initInputFields() {
    // Input fields      
    form.addField("cup", new TextField("cup"));
    form.addField("cig", new TextField("cig"));    
  }
  
  protected void initCreateButton() {
    HorizontalLayout buttonLayout = new HorizontalLayout();
    buttonLayout.setWidth(100, UNITS_PERCENTAGE);
    form.getFooter().setWidth(100, UNITS_PERCENTAGE);
    form.getFooter().addComponent(buttonLayout);
    
    Button createButton = new Button("Salva Contratto");
    buttonLayout.addComponent(createButton);
    buttonLayout.setComponentAlignment(createButton, Alignment.BOTTOM_RIGHT);
    
    createButton.addListener(new ClickListener() {
      public void buttonClick(ClickEvent event) {
        handleFormSubmit();
      }
    });
  }
  
  protected void handleFormSubmit() {
    try {
      // create contratto
      form.commit(); // will throw exception in case validation is false
      Contratto contratto = createContratto();
      
      // close popup and navigate to fresh contratto
      close();
      ExplorerApp.get().getViewManager().showContrattoPage(contratto.getId());
      
      
    } catch (InvalidValueException e) {
      // Do nothing: the Form component will render the errormsgs automatically
      setHeight(340, UNITS_PIXELS);
    }
  }
  
  protected Contratto createContratto() {
	Contratto contratto = contrattoService.newContratto(null);
	if(form.getField("cig").getValue() != null){
		contratto.setCig(form.getField("cig").getValue().toString());
	}
	if(form.getField("cup").getValue() != null){
		contratto.setCup(form.getField("cup").getValue().toString());
	}
	
	contrattoService.saveContratto(contratto);
	return contratto;
  }


}
