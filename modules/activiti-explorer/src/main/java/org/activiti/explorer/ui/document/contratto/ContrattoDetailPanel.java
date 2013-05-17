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

import java.io.InputStream;
import java.util.Set;

import org.activiti.engine.ContrattoService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.document.contratto.Contratto;
import org.activiti.engine.identity.Picture;
import org.activiti.engine.identity.User;
import org.activiti.explorer.Constants;
import org.activiti.explorer.ExplorerApp;
import org.activiti.explorer.I18nManager;
import org.activiti.explorer.Messages;
import org.activiti.explorer.data.LazyLoadingContainer;
import org.activiti.explorer.ui.Images;
import org.activiti.explorer.ui.custom.ConfirmationDialogPopupWindow;
import org.activiti.explorer.ui.custom.DetailPanel;
import org.activiti.explorer.ui.event.ConfirmationEvent;
import org.activiti.explorer.ui.event.ConfirmationEventListener;
import org.activiti.explorer.ui.event.SubmitEvent;
import org.activiti.explorer.ui.event.SubmitEventListener;
import org.activiti.explorer.ui.mainlayout.ExplorerLayout;
import org.activiti.explorer.ui.management.identity.GroupSelectionPopupWindow;
import org.activiti.explorer.ui.management.identity.GroupsForUserQuery;
import org.activiti.explorer.ui.management.identity.MemberShipChangeListener;
import org.activiti.explorer.ui.management.identity.NewUserPopupWindow;
import org.activiti.explorer.ui.management.identity.UserPage;
import org.activiti.explorer.ui.task.TaskRelatedContentComponent;

import com.vaadin.terminal.StreamResource;
import com.vaadin.terminal.StreamResource.StreamSource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.Reindeer;


/**
 * @author Joram Barrez
 */
public class ContrattoDetailPanel extends DetailPanel implements MemberShipChangeListener {

  private static final long serialVersionUID = 1L;
  
  protected transient IdentityService identityService;
  protected transient ContrattoService contrattoService;
  protected I18nManager i18nManager;
  
  protected ContrattoPage contrattoPage;
  protected Contratto contratto;  
    
  protected boolean editingDetails;
  protected HorizontalLayout contrattoDetailsLayout;
  protected TextField lastNameField;
  protected TextField emailField;
  protected TextField cigField;
  protected TextField cupField;
  protected HorizontalLayout groupLayout;
  protected Table groupTable;
  protected LazyLoadingContainer groupContainer;
  protected GroupsForUserQuery groupsForUserQuery;
  protected Label noGroupsLabel;
  
  public ContrattoDetailPanel(ContrattoPage contrattoPage, String contrattoId) {
    this.contrattoPage = contrattoPage;
    this.i18nManager = ExplorerApp.get().getI18nManager();
    this.identityService = ProcessEngines.getDefaultProcessEngine().getIdentityService();
    this.contrattoService = ProcessEngines.getDefaultProcessEngine().getContrattoService();
    this.contratto = contrattoService.createContrattoQuery().contrattoId(contrattoId).singleResult();   
    init();
  }
  
  protected void init() {
    setSizeFull();
    addStyleName(Reindeer.PANEL_LIGHT);
    
    initPageTitle();
    initContrattoDetails();
    initGroups();
    initGroups2();
    initGroups3();
    initGroups4();
    
    initActions();
  }
  
  
  protected void initPageTitle() {
	    HorizontalLayout layout = new HorizontalLayout();
	    layout.setWidth(100, UNITS_PERCENTAGE);
	    layout.setSpacing(true);
	    layout.setMargin(false, false, true, false);
	    layout.addStyleName(ExplorerLayout.STYLE_TITLE_BLOCK);
	    addDetailComponent(layout);
	    
	    //Embedded userImage = new Embedded(null, Images.USER_50);
	    //layout.addComponent(userImage);
	    
	    Label contrattoName = new Label("Contratto " + contratto.getId());
	    contrattoName.setSizeUndefined();
	    contrattoName.addStyleName(Reindeer.LABEL_H2);
	    layout.addComponent(contrattoName);
	    layout.setComponentAlignment(contrattoName, Alignment.MIDDLE_LEFT);
	    layout.setExpandRatio(contrattoName, 1.0f);
  }
  
  protected void initContrattoDetails() {
	    //Label userDetailsHeader = new Label(i18nManager.getMessage(Messages.USER_HEADER_DETAILS));
		Label contractDetailsHeader = new Label(i18nManager.getMessage(Messages.USER_HEADER_DETAILS));
	    contractDetailsHeader.addStyleName(ExplorerLayout.STYLE_H3);
	    contractDetailsHeader.addStyleName(ExplorerLayout.STYLE_DETAIL_BLOCK);
	    addDetailComponent(contractDetailsHeader);
	    
	    // Details: picture and basic info
	    contrattoDetailsLayout = new HorizontalLayout();
	    contrattoDetailsLayout.setSpacing(true);
	    contrattoDetailsLayout.setMargin(false, false, true, false);
	    addDetailComponent(contrattoDetailsLayout);
	    
	    populateContrattoDetails();
   }
  
   protected void populateContrattoDetails() {
	    loadContrattoDetails();
	    initDetailsActions();
	}
   
     
   protected void loadContrattoDetails() {
	    // Grid of details
	    GridLayout detailGrid = new GridLayout();
	    detailGrid.setColumns(2);
	    detailGrid.setSpacing(true);
	    detailGrid.setMargin(true, true, false, true);
	    contrattoDetailsLayout.addComponent(detailGrid);
	    
	    // Details
	    addContractDetail(detailGrid, "ID", new Label(contratto.getId())); // details are non-editable
	    if (!editingDetails) {
	      addContractDetail(detailGrid, "Cig", new Label(contratto.getCig()));
	      addContractDetail(detailGrid, "Cup", new Label(contratto.getCup()));
	    } else {
	      cigField = new TextField(null,contratto.getCig() != null ? contratto.getCig() : "");
	      addContractDetail(detailGrid, "Cig", cigField);
	      
	      cupField = new TextField(null,contratto.getCup() != null ? contratto.getCup() : "");
	      addContractDetail(detailGrid, "Cup", cupField);	        
	      
	    }
	 }
   
   protected void initDetailsActions() {
	    VerticalLayout actionLayout = new VerticalLayout();
	    actionLayout.setSpacing(true);
	    actionLayout.setMargin(false, false, false, true);
	    contrattoDetailsLayout.addComponent(actionLayout);
	    
	    if (!editingDetails) {
	      initEditButton(actionLayout);
	      initDeleteButton(actionLayout);
	    } else {
	      initSaveButton(actionLayout);
	    }
	  }
  
 
  

  protected void initEditButton(VerticalLayout actionLayout) {
    Button editButton = new Button("Modifica");
    editButton.addStyleName(Reindeer.BUTTON_SMALL);
    actionLayout.addComponent(editButton);
    editButton.addListener(new ClickListener() {
      public void buttonClick(ClickEvent event) {
        editingDetails = true;
        contrattoDetailsLayout.removeAllComponents();
        populateContrattoDetails(); // the layout will be populated differently since the 'editingDetails' boolean is set
      }
    });
  }
  
  protected void initDeleteButton(VerticalLayout actionLayout) {
	    Button deleteButton = new Button("Delete Contratto");
	    deleteButton.addStyleName(Reindeer.BUTTON_SMALL);
	    actionLayout.addComponent(deleteButton);
	    deleteButton.addListener(new ClickListener() {
	    	public void buttonClick(ClickEvent event) {
	            ConfirmationDialogPopupWindow confirmPopup = 
	              new ConfirmationDialogPopupWindow("Confermare cancellazione contratto", contratto.getId());
	            
	            confirmPopup.addListener(new ConfirmationEventListener() {
	              protected void rejected(ConfirmationEvent event) {
	              }
	              protected void confirmed(ConfirmationEvent event) {
	            	  // Delete contratto from database
	            	  contrattoService.deleteContratto(contratto.getId());
	            	  // Update ui
	            	  contrattoPage.refreshSelectNext();
	            	  
	              }
	            });
	            
	            ExplorerApp.get().getViewManager().showPopupWindow(confirmPopup);
	          }

	    });
	  }
  
  /*
   * 
   * protected void initDeleteButton(VerticalLayout actionLayout) {
    Button deleteButton = new Button(i18nManager.getMessage(Messages.USER_DELETE));
    deleteButton.addStyleName(Reindeer.BUTTON_SMALL);
    actionLayout.addComponent(deleteButton);
    deleteButton.addListener(new ClickListener() {
      public void buttonClick(ClickEvent event) {
        ConfirmationDialogPopupWindow confirmPopup = 
          new ConfirmationDialogPopupWindow(i18nManager.getMessage(Messages.USER_CONFIRM_DELETE, user.getId()));
        
        confirmPopup.addListener(new ConfirmationEventListener() {
          protected void rejected(ConfirmationEvent event) {
          }
          protected void confirmed(ConfirmationEvent event) {
            // Delete user from database
            identityService.deleteUser(user.getId());

            // Update ui
            userPage.refreshSelectNext();
            
            // Update user cache
            ExplorerApp.get().getUserCache().notifyUserDataChanged(user.getId());
          }
        });
        
        ExplorerApp.get().getViewManager().showPopupWindow(confirmPopup);
      }
    });
  }
   */
  
  
  
  
  protected void initSaveButton(VerticalLayout actionLayout) {
    Button saveButton = new Button("Salva");
    saveButton.addStyleName(Reindeer.BUTTON_SMALL);
    actionLayout.addComponent(saveButton);
    saveButton.addListener(new ClickListener() {
      public void buttonClick(ClickEvent event) {
        //Change Data
    	contratto.setCig(cigField.getValue().toString());
    	contratto.setCup(cupField.getValue().toString());
        
    	contrattoService.saveContratto(contratto);
    	//Refresh Detail Panel
    	editingDetails = false;
    	contrattoDetailsLayout.removeAllComponents();
    	populateContrattoDetails();
    	      
      }
    });
  } 
  
  
  protected void initActions() {
	    Button createContrattoButton = new Button("Nuovo Contratto");
	    //createUserButton.setIcon(Images.USER_16);
	    
	    createContrattoButton.addListener(new ClickListener() {
	      private static final long serialVersionUID = 1L;
	      public void buttonClick(ClickEvent event) {
	        NewContrattoPopupWindow newContrattoPopupWindow = new NewContrattoPopupWindow();
	        ExplorerApp.get().getViewManager().showPopupWindow(newContrattoPopupWindow);
	      }
	    });
	    
	    contrattoPage.getToolBar().removeAllButtons();
	    contrattoPage.getToolBar().addButton(createContrattoButton);
	  }  
	  
	  protected void addContractDetail(GridLayout detailLayout, String detail, Component value) {
	    Label label = new Label(detail + ": ");
	    label.addStyleName(ExplorerLayout.STYLE_LABEL_BOLD);
	    detailLayout.addComponent(label);
	    detailLayout.addComponent(value);
	  }  
  
  protected void initGroups() {
    HorizontalLayout groupHeader = new HorizontalLayout();
    groupHeader.setWidth(100, UNITS_PERCENTAGE);
    groupHeader.setSpacing(true);
    groupHeader.setMargin(false, false, true, false);
    groupHeader.addStyleName(ExplorerLayout.STYLE_DETAIL_BLOCK);
    addDetailComponent(groupHeader);
    
    initGroupTitle(groupHeader);
    initAddGroupsButton(groupHeader);
    
    groupLayout = new HorizontalLayout(); // we wrap the table in a simple layout so we can remove the table easy later on
    groupLayout.setWidth(100, UNITS_PERCENTAGE);
    addDetailComponent(groupLayout);
    //initGroupsTable();
  }
  
  protected void initGroups2() {
	    HorizontalLayout groupHeader = new HorizontalLayout();
	    groupHeader.setWidth(100, UNITS_PERCENTAGE);
	    groupHeader.setSpacing(true);
	    groupHeader.setMargin(false, false, true, false);
	    groupHeader.addStyleName(ExplorerLayout.STYLE_DETAIL_BLOCK);
	    addDetailComponent(groupHeader);
	    
	    initGroupTitle2(groupHeader);
	    initAddGroupsButton2(groupHeader);
	    
	    groupLayout = new HorizontalLayout(); // we wrap the table in a simple layout so we can remove the table easy later on
	    groupLayout.setWidth(100, UNITS_PERCENTAGE);
	    addDetailComponent(groupLayout);
	    //initGroupsTable();
	  }
  
  protected void initGroups3() {
	    HorizontalLayout groupHeader = new HorizontalLayout();
	    groupHeader.setWidth(100, UNITS_PERCENTAGE);
	    groupHeader.setSpacing(true);
	    groupHeader.setMargin(false, false, true, false);
	    groupHeader.addStyleName(ExplorerLayout.STYLE_DETAIL_BLOCK);
	    addDetailComponent(groupHeader);
	    
	    initGroupTitle3(groupHeader);
	    initAddGroupsButton3(groupHeader);
	    
	    groupLayout = new HorizontalLayout(); // we wrap the table in a simple layout so we can remove the table easy later on
	    groupLayout.setWidth(100, UNITS_PERCENTAGE);
	    addDetailComponent(groupLayout);
	    //initGroupsTable();
	  }
  
  protected void initGroups4() {
	    HorizontalLayout groupHeader = new HorizontalLayout();
	    groupHeader.setWidth(100, UNITS_PERCENTAGE);
	    groupHeader.setSpacing(true);
	    groupHeader.setMargin(false, false, true, false);
	    groupHeader.addStyleName(ExplorerLayout.STYLE_DETAIL_BLOCK);
	    addDetailComponent(groupHeader);
	    
	    initGroupTitle4(groupHeader);
	    initAddGroupsButton4(groupHeader);
	    
	    groupLayout = new HorizontalLayout(); // we wrap the table in a simple layout so we can remove the table easy later on
	    groupLayout.setWidth(100, UNITS_PERCENTAGE);
	    addDetailComponent(groupLayout);
	    //initGroupsTable();
	  }

  protected void initGroupTitle(HorizontalLayout groupHeader) {
    Label groupsTitle = new Label("Contenuto correlato");
    groupsTitle.addStyleName(ExplorerLayout.STYLE_H3);
    groupHeader.addComponent(groupsTitle);
  }
  
  protected void initGroupTitle2(HorizontalLayout groupHeader) {
	    Label groupsTitle = new Label("Delibera");
	    groupsTitle.addStyleName(ExplorerLayout.STYLE_H3);
	    groupHeader.addComponent(groupsTitle);
	  }
  
  protected void initGroupTitle3(HorizontalLayout groupHeader) {
	    Label groupsTitle = new Label("Determina");
	    groupsTitle.addStyleName(ExplorerLayout.STYLE_H3);
	    groupHeader.addComponent(groupsTitle);
	  }
  
  protected void initGroupTitle4(HorizontalLayout groupHeader) {
	    Label groupsTitle = new Label("Fascicolo");
	    groupsTitle.addStyleName(ExplorerLayout.STYLE_H3);
	    groupHeader.addComponent(groupsTitle);
	  }

  protected void initAddGroupsButton(HorizontalLayout groupHeader) {
    Button addRelatedContentButton = new Button();
    addRelatedContentButton.addStyleName(ExplorerLayout.STYLE_ADD);
    groupHeader.addComponent(addRelatedContentButton);
    groupHeader.setComponentAlignment(addRelatedContentButton, Alignment.MIDDLE_RIGHT);
    
    addRelatedContentButton.addListener(new ClickListener() {
      private static final long serialVersionUID = 1L;

      public void buttonClick(ClickEvent event) {
        
      }
    });
  }
  
  protected void initAddGroupsButton2(HorizontalLayout groupHeader) {
	    Button addRelatedContentButton = new Button();
	    addRelatedContentButton.addStyleName(ExplorerLayout.STYLE_ADD);
	    groupHeader.addComponent(addRelatedContentButton);
	    groupHeader.setComponentAlignment(addRelatedContentButton, Alignment.MIDDLE_RIGHT);
	    
	    addRelatedContentButton.addListener(new ClickListener() {
	      private static final long serialVersionUID = 1L;

	      public void buttonClick(ClickEvent event) {
	        
	      }
	    });
	  }
  
  protected void initAddGroupsButton3(HorizontalLayout groupHeader) {
	    Button addRelatedContentButton = new Button();
	    addRelatedContentButton.addStyleName(ExplorerLayout.STYLE_ADD);
	    groupHeader.addComponent(addRelatedContentButton);
	    groupHeader.setComponentAlignment(addRelatedContentButton, Alignment.MIDDLE_RIGHT);
	    
	    addRelatedContentButton.addListener(new ClickListener() {
	      private static final long serialVersionUID = 1L;

	      public void buttonClick(ClickEvent event) {
	        
	      }
	    });
	  }
  
  protected void initAddGroupsButton4(HorizontalLayout groupHeader) {
	    Button addRelatedContentButton = new Button();
	    addRelatedContentButton.addStyleName(ExplorerLayout.STYLE_ADD);
	    groupHeader.addComponent(addRelatedContentButton);
	    groupHeader.setComponentAlignment(addRelatedContentButton, Alignment.MIDDLE_RIGHT);
	    
	    addRelatedContentButton.addListener(new ClickListener() {
	      private static final long serialVersionUID = 1L;

	      public void buttonClick(ClickEvent event) {
	        
	      }
	    });
	  }
  
  /*
  protected void initGroupsTable() {
    groupsForUserQuery = new GroupsForUserQuery(identityService, this, user.getId());
    if (groupsForUserQuery.size() > 0) {
      groupTable = new Table();
      groupTable.setSortDisabled(true);
      groupTable.setHeight(150, UNITS_PIXELS);
      groupTable.setWidth(100, UNITS_PERCENTAGE);
      groupLayout.addComponent(groupTable);
      
      groupContainer = new LazyLoadingContainer(groupsForUserQuery, 30);
      groupTable.setContainerDataSource(groupContainer);
      
      groupTable.addContainerProperty("id", Button.class, null);
      groupTable.setColumnExpandRatio("id", 22);
      groupTable.addContainerProperty("name", String.class, null);
      groupTable.setColumnExpandRatio("name", 45);
      groupTable.addContainerProperty("type", String.class, null);
      groupTable.setColumnExpandRatio("type", 22);
      groupTable.addContainerProperty("actions", Component.class, null);
      groupTable.setColumnExpandRatio("actions", 11);
      groupTable.setColumnAlignment("actions", Table.ALIGN_CENTER);

    } else {
      noGroupsLabel = new Label(i18nManager.getMessage(Messages.USER_NO_GROUPS));
      groupLayout.addComponent(noGroupsLabel);
    }
  }
  */
  
  
  
  public void notifyMembershipChanged() {
    groupLayout.removeAllComponents();
    //initGroupsTable();
  }
  
}
