package models.role;

public class Role {
    private String id;
    private String description;

    public Role(String id, String description) {
        this.id = id;
        this.description = description;
    }

    public Role() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getVNeseDescription(){
        switch(this.id){
            case "0":
                return "Admin";
            case "1":
                return "Hiệu trưởng";
            case "2":
                return "Academic staff";
            case "3":
                return "Kế toán";
            case "4":
                return "Giáo viên";
            case "5":
                return "Phụ huynh";
          
        }
        return "";
    }
}
