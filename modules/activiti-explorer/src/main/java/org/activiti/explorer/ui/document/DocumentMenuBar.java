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

package org.activiti.explorer.ui.document;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.util.List;

import org.activiti.engine.IdentityService;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.identity.Group;
import org.activiti.explorer.ExplorerApp;
import org.activiti.explorer.I18nManager;
import org.activiti.explorer.Messages;
import org.activiti.explorer.ViewManager;
import org.activiti.explorer.identity.LoggedInUser;
import org.activiti.explorer.ui.Images;
import org.activiti.explorer.ui.custom.ToolBar;
import org.activiti.explorer.ui.custom.ToolbarEntry;
import org.activiti.explorer.ui.custom.ToolbarEntry.ToolbarCommand;
import org.activiti.explorer.ui.custom.ToolbarPopupEntry;
import org.activiti.explorer.ui.task.NewCasePopupWindow;
import org.activiti.explorer.ui.task.data.ArchivedListQuery;
import org.activiti.explorer.ui.task.data.InboxListQuery;
import org.activiti.explorer.ui.task.data.InvolvedListQuery;
import org.activiti.explorer.ui.task.data.QueuedListQuery;
import org.activiti.explorer.ui.task.data.TasksListQuery;

import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

/**
 * The menu bar which is shown when 'Documents' is selected in the main menu.
 * 
 
 */
public class DocumentMenuBar extends ToolBar {
  
  private static final long serialVersionUID = 1L;
  
  public static final String ENTRY_FASCICOLI = "fascicoli";
  public static final String ENTRY_DELIBERE = "delibere";
  public static final String ENTRY_DETERMINE = "determine";
  public static final String ENTRY_CONTRATTI = "contratti";
  public static final String ENTRY_FATTURE = "fatture";
  public static final String ENTRY_FIDEJUSSIONI = "fidejussioni";
  public static final String ENTRY_AUTORIZZAZIONI_DIP_PUBBLICI = "autorizzazioniDipendentiPubblici";
  public static final String ENTRY_TRACCIABILITA = "tracciabilita";
  public static final String ENTRY_DURC = "durc";
  public static final String ENTRY_AUTOCERTIFICAZIONI_DURC = "autocertificazioniDurc";
  public static final String ENTRY_VERBALI_VERIFICA_COLLAUDO = "verbaliVerificaECollaudo";
  public static final String ENTRY_IMPRESE = "imprese";
  public static final String ENTRY_PERSONE_FISICHE = "personeFisiche";
  public static final String ENTRY_UNIVERSITA = "universita";
  public static final String ENTRY_ENTI = "enti";
  
  
  protected ViewManager viewManager;
  protected I18nManager i18nManager;
  
  public DocumentMenuBar() {
    this.viewManager = ExplorerApp.get().getViewManager();
    this.i18nManager = ExplorerApp.get().getI18nManager();
    setWidth("100%");
    
    initToolbarEntries();
   
  }
  
  protected void initToolbarEntries() {  
	  this.addFascicoliToolbarEntry();
	  this.addDelibereToolbarEntry();  
	  this.addDetermineToolbarEntry();
	  this.addContrattiToolbarEntry();
	  this.addFattureToolbarEntry();
	 // this.addFidejussioniToolbarEntry();
	  //this.addAutorizzazioniDipendentiPubbliciToolbarEntry();
	  //this.addTracciabilitaToolbarEntry();
	  //this.addDurcToolbarEntry();
	  //this.addAutocertificazioneDurcToolbarEntry();
	  //this.addVerbaleVerificaECollaudoToolbarEntry();
	  this.addImpresaToolbarEntry();
	  this.addPersonaFisicaToolbarEntry();
	  this.addUniversitaToolbarEntry();
	  this.addEntiToolbarEntry();
    
  }
  
  protected void addFascicoliToolbarEntry(){
	  // Fascicoli
	  
	  addToolbarEntry(ENTRY_FASCICOLI, "Fascicoli", new ToolbarCommand() {
	      public void toolBarItemSelected() {
	    	  viewManager.showContrattoPage();
	      }
	  });
	  
  }
  
  protected void addContrattiToolbarEntry(){
	  // Contratti
	  
	  addToolbarEntry(ENTRY_CONTRATTI, i18nManager.getMessage(Messages.DOCUMENT_MENU_CONTRATTI), new ToolbarCommand() {
	      public void toolBarItemSelected() {
	    	  viewManager.showContrattoPage();
	      }
	  });
	  
  }
  
  protected void addFattureToolbarEntry(){
	  // Fatture
	    
	  addToolbarEntry(ENTRY_FATTURE, i18nManager.getMessage(Messages.DOCUMENT_MENU_FATTURE), new ToolbarCommand() {
	      public void toolBarItemSelected() {
	    	  viewManager.showContrattoPage();
	      }
	   });
	  
  }
  
  protected void addDetermineToolbarEntry(){
	  // Determine
	    
	  addToolbarEntry(ENTRY_DETERMINE, i18nManager.getMessage(Messages.DOCUMENT_MENU_DETERMINE), new ToolbarCommand() {
	      public void toolBarItemSelected() {
	    	  viewManager.showContrattoPage();
	      }
	  }); 
	  
  }
  
  protected void addDelibereToolbarEntry(){
	// Delibere
	    
	addToolbarEntry(ENTRY_DELIBERE, i18nManager.getMessage(Messages.DOCUMENT_MENU_DELIBERE), new ToolbarCommand() {
	      public void toolBarItemSelected() {
	    	  viewManager.showContrattoPage();
	      }
	    }); 
	  
  }
  
  protected void addFidejussioniToolbarEntry(){
		    
		addToolbarEntry(ENTRY_FIDEJUSSIONI, "Fidejussioni", new ToolbarCommand() {
		      public void toolBarItemSelected() {
		    	  viewManager.showContrattoPage();
		      }
		    }); 
		  
	}
  

protected void addAutorizzazioniDipendentiPubbliciToolbarEntry(){
	   
	addToolbarEntry(ENTRY_AUTORIZZAZIONI_DIP_PUBBLICI, "AutorizzazioniDipendentiPubblici", new ToolbarCommand() {
	      public void toolBarItemSelected() {
	    	  viewManager.showContrattoPage();
	      }
	    }); 
	  
 }

protected void addTracciabilitaToolbarEntry(){
	   
	addToolbarEntry(ENTRY_TRACCIABILITA, "Tracciabilita", new ToolbarCommand() {
	      public void toolBarItemSelected() {
	    	  viewManager.showContrattoPage();
	      }
	    }); 
	  
 }

protected void addDurcToolbarEntry(){
	   
	addToolbarEntry(ENTRY_DURC, "Durc", new ToolbarCommand() {
	      public void toolBarItemSelected() {
	    	  viewManager.showContrattoPage();
	      }
	    }); 
	  
 }

protected void addAutocertificazioneDurcToolbarEntry(){
	   
	addToolbarEntry(ENTRY_AUTOCERTIFICAZIONI_DURC, "AutocertificazioniDurc", new ToolbarCommand() {
	      public void toolBarItemSelected() {
	    	  viewManager.showContrattoPage();
	      }
	    }); 
	  
 }

protected void addVerbaleVerificaECollaudoToolbarEntry(){
	   
	addToolbarEntry(ENTRY_VERBALI_VERIFICA_COLLAUDO, "VerbaliVerificaECollaudo", new ToolbarCommand() {
	      public void toolBarItemSelected() {
	    	  viewManager.showContrattoPage();
	      }
	    }); 
	  
 }

protected void addImpresaToolbarEntry(){
	   
	addToolbarEntry(ENTRY_IMPRESE, "Imprese", new ToolbarCommand() {
	      public void toolBarItemSelected() {
	    	  viewManager.showContrattoPage();
	      }
	    }); 
	  
 }

protected void addPersonaFisicaToolbarEntry(){
	   
	addToolbarEntry(ENTRY_PERSONE_FISICHE, "PersoneFisiche", new ToolbarCommand() {
	      public void toolBarItemSelected() {
	    	  viewManager.showContrattoPage();
	      }
	    }); 
	  
 }

protected void addUniversitaToolbarEntry(){
	   
	addToolbarEntry(ENTRY_UNIVERSITA, "Universita", new ToolbarCommand() {
	      public void toolBarItemSelected() {
	    	  viewManager.showContrattoPage();
	      }
	    }); 
	  
 }

protected void addEntiToolbarEntry(){
	   
	addToolbarEntry(ENTRY_ENTI, "Enti", new ToolbarCommand() {
	      public void toolBarItemSelected() {
	    	  viewManager.showContrattoPage();
	      }
	    }); 
	  
 } 
  
}
