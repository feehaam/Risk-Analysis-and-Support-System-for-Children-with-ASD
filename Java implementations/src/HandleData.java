
public class HandleData {

      private String path, dataOriginal;
      private RASS algo = new RASS();
      private String readings[][];
        
      public String setPath(String s) {
            try {
                  path = s;
                  dataOriginal = FFiles.read(path);
                  if (dataOriginal.length() > 100) {
                        String [] row = dataOriginal.split("\n");
                        readings = new String[row.length][0];
                        for(int i=0; i<row.length; i++){
                              row[i] = row[i].replace("\t", " ");
                              String[] values = row[i].split(" ");
                              readings[i] = values;
                        }
                        return "[>] Data loaded.";
                  } else {
                        return "[>] Failed to load data!";
                  }
            } catch (Exception e) {
                  return "[>] Failed to load data!";
            }
      }
      
      public String runAlgo(){
            try{
                  algo.riskList.clear();
                  for(int i=0; i<readings.length; i++){
                        String[] values = readings[i];

                        for(int j=0; j<values.length; j++){
                              if(j==0) algo.readingNumber = Integer.parseInt(values[j]);
                              if(j==1) algo.gasVal = Integer.parseInt(values[j]);
                              if(j==2) algo.tempVal1 = Double.parseDouble(values[j]);
                              if(j==3) algo.presVal = (int) Double.parseDouble(values[j]);
                              if(j==4) algo.altiVal = Double.parseDouble(values[j]);
                              if(j==5) algo.seaPressVal = (int) Double.parseDouble(values[j]);
                              if(j==6) algo.realAltiVal = Double.parseDouble(values[j]);
                              if(j==7) algo.irVal = Integer.parseInt(values[j]);
                              if(j==8) algo.bpmVal = Integer.parseInt(values[j]);
                              if(j==9) algo.tempVal2 = Double.parseDouble(values[j]);
                              if(j==10) algo.accVal = Double.parseDouble(values[j]);
                              if(j==11) algo.gyroVal = Double.parseDouble(values[j]);
                              if(j==12) algo.anguVal = Double.parseDouble(values[j]);
                        }

                        algo.updateValues();
                        algo.riskAnalysis();
                        algo.readingNumber++;
                  }
                  return algo.getRisks();
            }
            catch(Exception e){
                  return "[>] Error reading data!";
            }
      }
      
      public String update(int inputNo, int parameterNo, int percent){
            String valS = readings[inputNo][parameterNo];
            double val = Double.parseDouble(valS);
            val = val + (val / 100 * percent);
            readings[inputNo][parameterNo] = String.valueOf(val);
            if(parameterNo == 8 || parameterNo == 1) {
                  readings[inputNo][parameterNo] = String.valueOf((int) val);
            }
            String ret = "";
            for(String x: readings[inputNo]) ret += x+" ";
            return ret;
      }
}
