package car.com.wlc.cardemo.javaBean;

/**
 * Created by Administrator on 2016/12/12.
 */

public class Contact {
   public static final String KEY="8da849223f31407f8602dabb54ddeb92";
   public static String URL="https://58.215.50.52:38443/openapi/openapi";
   public static String USERINFO="UserInfo";
   public final static   String CARID="VehicleInfoActivity";
   public final static  String ADDCARKEY="AddCarActivity";
   public final static String USERCARKEY="UserFragment";

   public final static String TRANSMISSIONTYPE[]={ "自动(AT)","手动(MT)","无级变速(CVT)","双离合变速(DSG)","序列变速箱(AMT)"};
      public final static String VEHICLETYPE[]={"轿车" , "MPV" ,"SUV","越野车" , "轻型客车" ,"大客车"  ,"轻型卡车"  ,"重型卡车",   "工程车辆"};
   public final static String FUELTYPE[]={"0#柴油","90#(京89#)汽油","93#(京92#)汽油","97#(京95#)汽油"};
   public static final String EMISSIONSTD[]={"国I","国2","国3","国4","欧1","欧2","欧3","欧4"};
   public static final String SERVICE[]={"用途未知","私家车","出租车","租赁车","拼车","货车"};
   public static String getEmissionType(int i){
      if (i!=-1){
         return EMISSIONSTD[i];
      }
      return null;
   }
   public static String getService(int i){
      if (i==0){
         return SERVICE[0];
      }
      if (i==1){
         return SERVICE[1];
      }
      if (i==200){
         return SERVICE[2];
      }
      if (i==201){
         return SERVICE[3];
      }
      if (i==202){
         return SERVICE[4];
      }
      if (i==203){
         return SERVICE[5];
      }

      return null;
   }
   public static String getVehicleType(String i){
      if (i.equals("1")){
         return VEHICLETYPE[0];
      }
      else  if (i.equals("2")){
         return VEHICLETYPE[1];
      }
      else if (i.equals("3")){
         return VEHICLETYPE[2];
      }
      else if (i.equals("4")){
         return VEHICLETYPE[3];
      }
      else if (i.equals("21")){
         return VEHICLETYPE[4];
      }
      else if (i.equals("22")){
         return VEHICLETYPE[5];
      }
      else if (i.equals("31")){
         return VEHICLETYPE[6];
      }
      else if (i.equals("32")){
         return VEHICLETYPE[7];
      }
      else if (i.equals("41")){
         return VEHICLETYPE[8];
      }
    else
         return "其他";
   }
   public static String getFueType(String  type){
      if (type.equals("0")){
         return FUELTYPE[0];
      }if (type.equals("90")){
         return FUELTYPE[1];
      }if (type.equals("93")){
         return FUELTYPE[2];
      }if (type.equals("97")){
         return FUELTYPE[3];
      }
      return null;
   }

}

