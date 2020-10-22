package Model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class ObjetoBase
{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int objCod_objBase;

    public int getObjCod_objBase() 
    {
        return objCod_objBase;
    }

    public void setObjCod_objBase(int objCod_objBase) 
    {
        this.objCod_objBase = objCod_objBase;
    }
    
    public ObjetoBase toObjeto(String pVetor) {
//        //System.out.println("Convertendo");
        Gson gson = new GsonBuilder().create();
        return gson.fromJson(pVetor, this.getClass());
    }
    
    public String toJson() {
        Gson gson = new GsonBuilder().create();        

        return gson.toJson(this, this.getClass());
    }
}
