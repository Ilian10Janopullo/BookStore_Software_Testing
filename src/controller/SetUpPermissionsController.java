package controller;

import dao.PermissionsDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import model.Role;
import model.UsersOfTheSystem;
import view.SetPermissionsView;

public class SetUpPermissionsController {
    private final SetPermissionsView view;
    private final ObservableList<String> permissionsCombo;
    private final PermissionsDAO permissionsDAO;
    private final Stage stage;
    private final UsersOfTheSystem user;

    public SetUpPermissionsController(UsersOfTheSystem user, Stage stage){
        this.user = user;
        this.stage = stage;
        this.permissionsDAO = new PermissionsDAO();
        permissionsCombo = FXCollections.observableArrayList(permissionsDAO.getAll());
        this.view = new SetPermissionsView();

        check();

        this.view.getBackBtn().setOnAction(this::Back);
        this.view.getUpdateBtn().setOnAction(this::Update);
    }

    public SetPermissionsView getView() {
        return view;
    }


    public void Update(ActionEvent event){

        if((!this.view.getManageStockLibrarian().isSelected() && !this.view.getManageSellsLibrarian().isSelected() && !this.view.getManageBillsLibrarian().isSelected() && !this.view.getCheckUsersLibrarian().isSelected())
                || (!this.view.getManageStockLibrarian().isSelected() && !this.view.getManageSellsLibrarian().isSelected() && !this.view.getManageBillsLibrarian().isSelected() && !this.view.getCheckUsersLibrarian().isSelected()) ){
            Alert alert;
            alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("You have to check at least one box per role!");
            alert.setTitle("Warning");
            alert.show();
            check();
        }
        else{

            permissionsCombo.clear();


            if(this.view.getManageStockManager().isSelected() && this.view.getManageSellsManager().isSelected() && this.view.getManageBillsManager().isSelected() && this.view.getCheckUsersManager().isSelected()){
                permissionsCombo.add("PermissionCombo15Controller");
            }else if(this.view.getManageSellsManager().isSelected() && this.view.getManageBillsManager().isSelected() && this.view.getCheckUsersManager().isSelected()){
                permissionsCombo.add("PermissionCombo14Controller");
            }else if(this.view.getManageStockManager().isSelected() && this.view.getManageSellsManager().isSelected() && this.view.getCheckUsersManager().isSelected()){
                permissionsCombo.add("PermissionCombo13Controller");
            } else if(this.view.getManageStockManager().isSelected() && this.view.getManageSellsManager().isSelected() && this.view.getManageBillsManager().isSelected()){
                permissionsCombo.add("PermissionCombo12Controller");
            } else if(this.view.getManageStockManager().isSelected() && this.view.getManageBillsManager().isSelected() && this.view.getCheckUsersManager().isSelected()){
                permissionsCombo.add("PermissionCombo11Controller");
            }  else if(this.view.getManageBillsManager().isSelected() && this.view.getCheckUsersManager().isSelected()){
                permissionsCombo.add("PermissionCombo10Controller");
            } else if(this.view.getManageSellsManager().isSelected() && this.view.getCheckUsersManager().isSelected()){
                permissionsCombo.add("PermissionCombo9Controller");
            } else if( this.view.getManageSellsManager().isSelected() && this.view.getManageBillsManager().isSelected()){
                permissionsCombo.add("PermissionCombo8Controller");
            } else if(this.view.getManageStockManager().isSelected()  && this.view.getCheckUsersManager().isSelected()){
                permissionsCombo.add("PermissionCombo7Controller");
            } else if(this.view.getManageStockManager().isSelected() && this.view.getManageBillsManager().isSelected()){
                permissionsCombo.add("PermissionCombo6Controller");
            } else if(this.view.getManageStockManager().isSelected() && this.view.getManageSellsManager().isSelected()){
                permissionsCombo.add("PermissionCombo5Controller");
            } else if(this.view.getCheckUsersManager().isSelected()){
                permissionsCombo.add("PermissionCombo4Controller");
            } else if(this.view.getManageBillsManager().isSelected()){
                permissionsCombo.add("PermissionCombo3Controller");
            } else if(this.view.getManageSellsManager().isSelected()){
                permissionsCombo.add("PermissionCombo2Controller");
            } else if(this.view.getManageStockManager().isSelected()){
                permissionsCombo.add("PermissionCombo1Controller");
            }

            if(this.view.getManageStockLibrarian().isSelected() && this.view.getManageSellsLibrarian().isSelected() && this.view.getManageBillsLibrarian().isSelected() && this.view.getCheckUsersLibrarian().isSelected()){
                permissionsCombo.add("PermissionCombo15Controller");
            }else if(this.view.getManageSellsLibrarian().isSelected() && this.view.getManageBillsLibrarian().isSelected() && this.view.getCheckUsersLibrarian().isSelected()){
                permissionsCombo.add("PermissionCombo14Controller");
            }else if(this.view.getManageStockLibrarian().isSelected() && this.view.getManageSellsLibrarian().isSelected() && this.view.getCheckUsersLibrarian().isSelected()){
                permissionsCombo.add("PermissionCombo13Controller");
            } else if(this.view.getManageStockLibrarian().isSelected() && this.view.getManageSellsLibrarian().isSelected() && this.view.getManageBillsLibrarian().isSelected()){
                permissionsCombo.add("PermissionCombo12Controller");
            } else if(this.view.getManageStockLibrarian().isSelected() && this.view.getManageBillsLibrarian().isSelected() && this.view.getCheckUsersLibrarian().isSelected()){
                permissionsCombo.add("PermissionCombo11Controller");
            }  else if(this.view.getManageBillsLibrarian().isSelected() && this.view.getCheckUsersLibrarian().isSelected()){
                permissionsCombo.add("PermissionCombo10Controller");
            } else if(this.view.getManageSellsLibrarian().isSelected() && this.view.getCheckUsersLibrarian().isSelected()){
                permissionsCombo.add("PermissionCombo9Controller");
            } else if( this.view.getManageSellsLibrarian().isSelected() && this.view.getManageBillsLibrarian().isSelected()){
                permissionsCombo.add("PermissionCombo8Controller");
            } else if(this.view.getManageStockLibrarian().isSelected()  && this.view.getCheckUsersLibrarian().isSelected()){
                permissionsCombo.add("PermissionCombo7Controller");
            } else if(this.view.getManageStockLibrarian().isSelected() && this.view.getManageBillsLibrarian().isSelected()){
                permissionsCombo.add("PermissionCombo6Controller");
            } else if(this.view.getManageStockLibrarian().isSelected() && this.view.getManageSellsLibrarian().isSelected()){
                permissionsCombo.add("PermissionCombo5Controller");
            } else if(this.view.getCheckUsersLibrarian().isSelected()){
                permissionsCombo.add("PermissionCombo4Controller");
            } else if(this.view.getManageBillsLibrarian().isSelected()){
                permissionsCombo.add("PermissionCombo3Controller");
            } else if(this.view.getManageSellsLibrarian().isSelected()){
                permissionsCombo.add("PermissionCombo2Controller");
            } else if(this.view.getManageStockLibrarian().isSelected()){
                permissionsCombo.add("PermissionCombo1Controller");
            }
            if(this.permissionsDAO.update(permissionsCombo)){
                Alert alert;
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Updated successfully!");
                alert.setTitle("Information");
                alert.show();
            }
            else{
                Alert alert;
                alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText("Updated failed!");
                alert.setTitle("Information");
                alert.show();
            }

        }

    }

    public void Back(ActionEvent event) {
        if (user.getRole() == Role.ADMIN) {
            AdminMenuController controller = new AdminMenuController(stage, user, true);
            stage.getScene().setRoot(controller.getView());
        }
    }

    public void check(){
        if (permissionsCombo.get(0).equals("PermissionCombo15Controller")) {
            this.view.setManageStockManager(true);
            this.view.setManageSellsManager(true);
            this.view.setManageBillsManager(true);
            this.view.setCheckUsersManager(true);
        } else if (permissionsCombo.get(0).equals("PermissionCombo14Controller")) {
            this.view.setManageStockManager(false);
            this.view.setManageSellsManager(true);
            this.view.setManageBillsManager(true);
            this.view.setCheckUsersManager(true);
        } else if (permissionsCombo.get(0).equals("PermissionCombo13Controller")) {
            this.view.setManageStockManager(true);
            this.view.setManageSellsManager(true);
            this.view.setManageBillsManager(false);
            this.view.setCheckUsersManager(true);
        } else if (permissionsCombo.get(0).equals("PermissionCombo12Controller")) {
            this.view.setManageStockManager(true);
            this.view.setManageSellsManager(true);
            this.view.setManageBillsManager(true);
            this.view.setCheckUsersManager(false);
        } else if (permissionsCombo.get(0).equals("PermissionCombo11Controller")) {
            this.view.setManageStockManager(true);
            this.view.setManageSellsManager(false);
            this.view.setManageBillsManager(true);
            this.view.setCheckUsersManager(true);
        } else if (permissionsCombo.get(0).equals("PermissionCombo10Controller")) {
            this.view.setManageStockManager(false);
            this.view.setManageSellsManager(false);
            this.view.setManageBillsManager(true);
            this.view.setCheckUsersManager(true);
        } else if (permissionsCombo.get(0).equals("PermissionCombo9Controller")) {
            this.view.setManageStockManager(false);
            this.view.setManageSellsManager(true);
            this.view.setManageBillsManager(false);
            this.view.setCheckUsersManager(true);
        } else if (permissionsCombo.get(0).equals("PermissionCombo8Controller")) {
            this.view.setManageStockManager(false);
            this.view.setManageSellsManager(true);
            this.view.setManageBillsManager(true);
            this.view.setCheckUsersManager(false);
        } else if (permissionsCombo.get(0).equals("PermissionCombo7Controller")) {
            this.view.setManageStockManager(true);
            this.view.setManageSellsManager(false);
            this.view.setManageBillsManager(false);
            this.view.setCheckUsersManager(true);
        } else if (permissionsCombo.get(0).equals("PermissionCombo6Controller")) {
            this.view.setManageStockManager(true);
            this.view.setManageSellsManager(false);
            this.view.setManageBillsManager(true);
            this.view.setCheckUsersManager(false);
        } else if (permissionsCombo.get(0).equals("PermissionCombo5Controller")) {
            this.view.setManageStockManager(true);
            this.view.setManageSellsManager(true);
            this.view.setManageBillsManager(false);
            this.view.setCheckUsersManager(false);
        } else if (permissionsCombo.get(0).equals("PermissionCombo4Controller")) {
            this.view.setManageStockManager(false);
            this.view.setManageSellsManager(false);
            this.view.setManageBillsManager(false);
            this.view.setCheckUsersManager(true);
        } else if (permissionsCombo.get(0).equals("PermissionCombo3Controller")) {
            this.view.setManageStockManager(false);
            this.view.setManageSellsManager(false);
            this.view.setManageBillsManager(true);
            this.view.setCheckUsersManager(false);
        } else if (permissionsCombo.get(0).equals("PermissionCombo2Controller")) {
            this.view.setManageStockManager(false);
            this.view.setManageSellsManager(true);
            this.view.setManageBillsManager(false);
            this.view.setCheckUsersManager(false);
        } else if (permissionsCombo.get(0).equals("PermissionCombo1Controller")) {
            this.view.setManageStockManager(true);
            this.view.setManageSellsManager(false);
            this.view.setManageBillsManager(false);
            this.view.setCheckUsersManager(false);
        }

        if (permissionsCombo.get(1).equals("PermissionCombo15Controller")) {
            this.view.setManageStockLibrarian(true);
            this.view.setManageSellsLibrarian(true);
            this.view.setManageBillsLibrarian(true);
            this.view.setCheckUsersLibrarian(true);
        } else if (permissionsCombo.get(1).equals("PermissionCombo14Controller")) {
            this.view.setManageStockLibrarian(false);
            this.view.setManageSellsLibrarian(true);
            this.view.setManageBillsLibrarian(true);
            this.view.setCheckUsersLibrarian(true);
        } else if (permissionsCombo.get(1).equals("PermissionCombo13Controller")) {
            this.view.setManageStockLibrarian(true);
            this.view.setManageSellsLibrarian(true);
            this.view.setManageBillsLibrarian(false);
            this.view.setCheckUsersLibrarian(true);
        } else if (permissionsCombo.get(1).equals("PermissionCombo12Controller")) {
            this.view.setManageStockLibrarian(true);
            this.view.setManageSellsLibrarian(true);
            this.view.setManageBillsLibrarian(true);
            this.view.setCheckUsersLibrarian(false);
        } else if (permissionsCombo.get(1).equals("PermissionCombo11Controller")) {
            this.view.setManageStockLibrarian(true);
            this.view.setManageSellsLibrarian(false);
            this.view.setManageBillsLibrarian(true);
            this.view.setCheckUsersLibrarian(true);
        } else if (permissionsCombo.get(1).equals("PermissionCombo10Controller")) {
            this.view.setManageStockLibrarian(false);
            this.view.setManageSellsLibrarian(false);
            this.view.setManageBillsLibrarian(true);
            this.view.setCheckUsersLibrarian(true);
        } else if (permissionsCombo.get(1).equals("PermissionCombo9Controller")) {
            this.view.setManageStockLibrarian(false);
            this.view.setManageSellsLibrarian(true);
            this.view.setManageBillsLibrarian(false);
            this.view.setCheckUsersLibrarian(true);
        } else if (permissionsCombo.get(1).equals("PermissionCombo8Controller")) {
            this.view.setManageStockLibrarian(false);
            this.view.setManageSellsLibrarian(true);
            this.view.setManageBillsLibrarian(true);
            this.view.setCheckUsersLibrarian(false);
        } else if (permissionsCombo.get(1).equals("PermissionCombo7Controller")) {
            this.view.setManageStockLibrarian(true);
            this.view.setManageSellsLibrarian(false);
            this.view.setManageBillsLibrarian(false);
            this.view.setCheckUsersLibrarian(true);
        } else if (permissionsCombo.get(1).equals("PermissionCombo6Controller")) {
            this.view.setManageStockLibrarian(true);
            this.view.setManageSellsLibrarian(false);
            this.view.setManageBillsLibrarian(true);
            this.view.setCheckUsersLibrarian(false);
        } else if (permissionsCombo.get(1).equals("PermissionCombo5Controller")) {
            this.view.setManageStockLibrarian(true);
            this.view.setManageSellsLibrarian(true);
            this.view.setManageBillsLibrarian(false);
            this.view.setCheckUsersLibrarian(false);
        } else if (permissionsCombo.get(1).equals("PermissionCombo4Controller")) {
            this.view.setManageStockLibrarian(false);
            this.view.setManageSellsLibrarian(false);
            this.view.setManageBillsLibrarian(false);
            this.view.setCheckUsersLibrarian(true);
        } else if (permissionsCombo.get(1).equals("PermissionCombo3Controller")) {
            this.view.setManageStockLibrarian(false);
            this.view.setManageSellsLibrarian(false);
            this.view.setManageBillsLibrarian(true);
            this.view.setCheckUsersLibrarian(false);
        } else if (permissionsCombo.get(1).equals("PermissionCombo2Controller")) {
            this.view.setManageStockLibrarian(false);
            this.view.setManageSellsLibrarian(true);
            this.view.setManageBillsLibrarian(false);
            this.view.setCheckUsersLibrarian(false);
        } else if (permissionsCombo.get(1).equals("PermissionCombo1Controller")) {
            this.view.setManageStockLibrarian(true);
            this.view.setManageSellsLibrarian(false);
            this.view.setManageBillsLibrarian(false);
            this.view.setCheckUsersLibrarian(false);
        }
    }
}
