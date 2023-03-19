
import java.awt.Dimension;

public class UI extends javax.swing.JFrame {
      public UI() {
            initComponents();
            setUp();
      }
      @SuppressWarnings("unchecked")
      
      HandleData data = new HandleData();
      private void setUp(){
      }
      // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
      private void initComponents() {

            jPanel1 = new javax.swing.JPanel();
            jPanel2 = new javax.swing.JPanel();
            jScrollPane1 = new javax.swing.JScrollPane();
            log = new javax.swing.JTextArea();
            jScrollPane2 = new javax.swing.JScrollPane();
            output = new javax.swing.JTextArea();
            jLabel7 = new javax.swing.JLabel();
            jLabel8 = new javax.swing.JLabel();
            filePicker = new javax.swing.JFileChooser();
            jLabel1 = new javax.swing.JLabel();
            jSeparator1 = new javax.swing.JSeparator();
            jPanel3 = new javax.swing.JPanel();
            choosenData = new javax.swing.JLabel();
            jPanel7 = new javax.swing.JPanel();
            slider2 = new javax.swing.JSlider();
            add2 = new javax.swing.JButton();
            jLabel6 = new javax.swing.JLabel();
            il2 = new javax.swing.JLabel();
            options2 = new javax.swing.JComboBox<>();
            datano2 = new javax.swing.JTextField();
            add3 = new javax.swing.JButton();

            setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

            jPanel1.setBackground(new java.awt.Color(57, 62, 70));

            jPanel2.setBackground(new java.awt.Color(34, 40, 49));
            jPanel2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(102, 102, 102), 1, true));
            jPanel2.setForeground(new java.awt.Color(204, 204, 204));

            log.setEditable(false);
            log.setBackground(new java.awt.Color(34, 40, 49));
            log.setColumns(20);
            log.setFont(new java.awt.Font("Lucida Sans Typewriter", 0, 13)); // NOI18N
            log.setForeground(new java.awt.Color(255, 255, 255));
            log.setRows(5);
            log.setFocusable(false);
            jScrollPane1.setViewportView(log);

            output.setEditable(false);
            output.setBackground(new java.awt.Color(34, 40, 49));
            output.setColumns(20);
            output.setFont(new java.awt.Font("Lucida Sans Typewriter", 0, 13)); // NOI18N
            output.setForeground(new java.awt.Color(255, 255, 255));
            output.setRows(5);
            output.setFocusable(false);
            jScrollPane2.setViewportView(output);

            jLabel7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
            jLabel7.setForeground(new java.awt.Color(255, 255, 255));
            jLabel7.setText("Log");

            jLabel8.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
            jLabel8.setForeground(new java.awt.Color(255, 255, 255));
            jLabel8.setText("Output");

            javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
            jPanel2.setLayout(jPanel2Layout);
            jPanel2Layout.setHorizontalGroup(
                  jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                  .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                              .addComponent(jScrollPane1)
                              .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 309, Short.MAX_VALUE)
                              .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                          .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
                                          .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())
            );
            jPanel2Layout.setVerticalGroup(
                  jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                  .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 323, Short.MAX_VALUE)
                        .addContainerGap())
            );

            filePicker.setFont(new java.awt.Font("Microsoft YaHei UI", 0, 11)); // NOI18N
            filePicker.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 4, true));
            filePicker.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
            filePicker.addActionListener(new java.awt.event.ActionListener() {
                  public void actionPerformed(java.awt.event.ActionEvent evt) {
                        filePickerActionPerformed(evt);
                  }
            });

            jLabel1.setFont(new java.awt.Font("OCR A Extended", 1, 48)); // NOI18N
            jLabel1.setForeground(new java.awt.Color(255, 255, 255));
            jLabel1.setText("RASS");

            jPanel3.setBackground(new java.awt.Color(34, 40, 49));
            jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

            choosenData.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
            choosenData.setForeground(new java.awt.Color(255, 255, 255));
            choosenData.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
            choosenData.setText("Choose a data file first");

            javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
            jPanel3.setLayout(jPanel3Layout);
            jPanel3Layout.setHorizontalGroup(
                  jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                  .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(choosenData, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
            );
            jPanel3Layout.setVerticalGroup(
                  jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                  .addComponent(choosenData, javax.swing.GroupLayout.DEFAULT_SIZE, 51, Short.MAX_VALUE)
            );

            jPanel7.setBackground(new java.awt.Color(34, 40, 49));
            jPanel7.setBorder(javax.swing.BorderFactory.createEtchedBorder());

            slider2.setBackground(new java.awt.Color(34, 40, 49));
            slider2.setMinimum(-100);
            slider2.setValue(0);
            slider2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
            slider2.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
                  public void mouseMoved(java.awt.event.MouseEvent evt) {
                        slider2MouseMoved(evt);
                  }
            });
            slider2.addMouseListener(new java.awt.event.MouseAdapter() {
                  public void mouseReleased(java.awt.event.MouseEvent evt) {
                        slider2MouseReleased(evt);
                  }
            });
            slider2.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
                  public void propertyChange(java.beans.PropertyChangeEvent evt) {
                        slider2PropertyChange(evt);
                  }
            });

            add2.setBackground(new java.awt.Color(34, 40, 49));
            add2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
            add2.setForeground(new java.awt.Color(255, 255, 255));
            add2.setText("Add");
            add2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
            add2.addActionListener(new java.awt.event.ActionListener() {
                  public void actionPerformed(java.awt.event.ActionEvent evt) {
                        add2ActionPerformed(evt);
                  }
            });

            jLabel6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
            jLabel6.setForeground(new java.awt.Color(255, 255, 255));
            jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
            jLabel6.setText("Choose an input parameter, a target data no, set data change amount (%) to modify data.");

            il2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
            il2.setForeground(new java.awt.Color(255, 255, 255));
            il2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
            il2.setText("0%");

            options2.setFont(new java.awt.Font("Lucida Fax", 0, 18)); // NOI18N
            options2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select input", "Air Quality", "Temperature", "Pressure", "Altitude", "BPM", "Acceleration", "Gyroscope", "Angular Rotation", " " }));
            options2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

            datano2.setBackground(new java.awt.Color(57, 62, 70));
            datano2.setForeground(new java.awt.Color(255, 255, 255));
            datano2.setHorizontalAlignment(javax.swing.JTextField.CENTER);
            datano2.setText("Input No");
            datano2.addMouseListener(new java.awt.event.MouseAdapter() {
                  public void mouseClicked(java.awt.event.MouseEvent evt) {
                        datano2MouseClicked(evt);
                  }
            });

            javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
            jPanel7.setLayout(jPanel7Layout);
            jPanel7Layout.setHorizontalGroup(
                  jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                  .addGroup(jPanel7Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                              .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 595, Short.MAX_VALUE)
                              .addGroup(jPanel7Layout.createSequentialGroup()
                                    .addComponent(options2, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(datano2, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(slider2, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(il2, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(add2, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap())
            );
            jPanel7Layout.setVerticalGroup(
                  jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                  .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                              .addComponent(slider2, javax.swing.GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE)
                              .addComponent(il2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                              .addComponent(add2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                              .addComponent(options2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE)
                              .addComponent(datano2))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            );

            add3.setBackground(new java.awt.Color(34, 40, 49));
            add3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
            add3.setForeground(new java.awt.Color(255, 255, 255));
            add3.setText("Run Risk Analysis");
            add3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
            add3.addActionListener(new java.awt.event.ActionListener() {
                  public void actionPerformed(java.awt.event.ActionEvent evt) {
                        add3ActionPerformed(evt);
                  }
            });

            javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
            jPanel1.setLayout(jPanel1Layout);
            jPanel1Layout.setHorizontalGroup(
                  jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                  .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                              .addComponent(jSeparator1)
                              .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                          .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addContainerGap()
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                      .addComponent(filePicker, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                      .addGroup(jPanel1Layout.createSequentialGroup()
                                                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 297, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                            .addGap(0, 0, Short.MAX_VALUE))
                                                      .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                      .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                                          .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(add3, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(168, 168, 168)))
                                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap())
            );
            jPanel1Layout.setVerticalGroup(
                  jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                  .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                              .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                              .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(filePicker, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(35, 35, 35)
                                    .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(33, 33, 33)
                                    .addComponent(add3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addContainerGap())
            );

            javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
            getContentPane().setLayout(layout);
            layout.setHorizontalGroup(
                  layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                  .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            );
            layout.setVerticalGroup(
                  layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                  .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            );

            pack();
            setLocationRelativeTo(null);
      }// </editor-fold>//GEN-END:initComponents

      private void slider2MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_slider2MouseMoved
            // TODO add your handling code here:
      }//GEN-LAST:event_slider2MouseMoved

      private void slider2MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_slider2MouseReleased
            il2.setText(slider2.getValue()+"%");
      }//GEN-LAST:event_slider2MouseReleased

      private void slider2PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_slider2PropertyChange
            // TODO add your handling code here:
      }//GEN-LAST:event_slider2PropertyChange

      private void datano2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_datano2MouseClicked
            int val = 0;
            try{
                  val = Integer.parseInt(datano2.getText());
            }
            catch(Exception e){
                  
            }
            datano2.setText(val+"");
      }//GEN-LAST:event_datano2MouseClicked

      private void filePickerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_filePickerActionPerformed
            String result = data.setPath(filePicker.getSelectedFile().getPath().toString());
            log.setText(log.getText()+result+"\n");
            if(result.contains(".")){
                  String dataFileName = filePicker.getSelectedFile().getPath().toString();
                  dataFileName = dataFileName.substring(dataFileName.lastIndexOf("\\")+1, dataFileName.length());
                  choosenData.setText("Selected data: "+dataFileName);
            }
            else{
                  choosenData.setText("Choose a data file first");
            }
      }//GEN-LAST:event_filePickerActionPerformed

      private void add3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_add3ActionPerformed
            output.setText("");
            String res = data.runAlgo();
            if(res.contains("Error reading data!")){
                  log.setText(log.getText()+res+"\n");
            }
            else{
                  log.setText(log.getText()+"[>] Risk analysis complete."+"\n");
                  output.setText(res);
            }
            System.out.println("-----------------------------------------------------------------------------");
      }//GEN-LAST:event_add3ActionPerformed

      private void add2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_add2ActionPerformed
            String result = "";
            boolean done = true;
            String ret = null;
            try{
                  String parameter = options2.getSelectedItem().toString();
                  int inputNo = Integer.parseInt(datano2.getText());
                  int inc = slider2.getValue();
                  result = parameter;

                  switch(parameter){
                        case "Air Quality": ret = data.update(inputNo, 1, inc);
                        break;
                        case "Temperature": ret = data.update(inputNo, 2, inc);
                                            ret = data.update(inputNo, 9, inc);
                        break;
                        case "Pressure": ret = data.update(inputNo, 3, inc);
                        break;
                        case "Altitude": ret = data.update(inputNo, 4, inc);
                        break;
                        case "BPM": ret = data.update(inputNo, 8, inc);
                        break;
                        case "Acceleration": ret = data.update(inputNo, 10, inc);
                        break;
                        case "Gyroscope": ret = data.update(inputNo, 11, inc);
                        break;
                        case "Angular Rotation": ret = data.update(inputNo, 12, inc);
                        break;
                  }
            }
            catch(Exception e){
                  done = false;
            }
            
            if(result.equals("Select input") || result.equals("")){
                  result = "[>] Select an input first!";
            }
            else if(!done) {
                  result = "[>] " + result + " could not be updated!";
            }
            else {
                  result = "[>] " + result + " is updated."+"\n[>] "+ret;
            }
            log.setText(log.getText()+result+"\n");
      }//GEN-LAST:event_add2ActionPerformed

      public static void main(String args[]) {
            try {
                  for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                        if ("Nimbus".equals(info.getName())) {
                              javax.swing.UIManager.setLookAndFeel(info.getClassName());
                              break;
                        }
                  }
            } catch (ClassNotFoundException ex) {
                  java.util.logging.Logger.getLogger(UI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            } catch (InstantiationException ex) {
                  java.util.logging.Logger.getLogger(UI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                  java.util.logging.Logger.getLogger(UI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            } catch (javax.swing.UnsupportedLookAndFeelException ex) {
                  java.util.logging.Logger.getLogger(UI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            }
            //</editor-fold>

            java.awt.EventQueue.invokeLater(new Runnable() {
                  public void run() {
                        new UI().setVisible(true);
                  }
            });
      }

      // Variables declaration - do not modify//GEN-BEGIN:variables
      private javax.swing.JButton add2;
      private javax.swing.JButton add3;
      private javax.swing.JLabel choosenData;
      private javax.swing.JTextField datano2;
      private javax.swing.JFileChooser filePicker;
      private javax.swing.JLabel il2;
      private javax.swing.JLabel jLabel1;
      private javax.swing.JLabel jLabel6;
      private javax.swing.JLabel jLabel7;
      private javax.swing.JLabel jLabel8;
      private javax.swing.JPanel jPanel1;
      private javax.swing.JPanel jPanel2;
      private javax.swing.JPanel jPanel3;
      private javax.swing.JPanel jPanel7;
      private javax.swing.JScrollPane jScrollPane1;
      private javax.swing.JScrollPane jScrollPane2;
      private javax.swing.JSeparator jSeparator1;
      private javax.swing.JTextArea log;
      private javax.swing.JComboBox<String> options2;
      private javax.swing.JTextArea output;
      private javax.swing.JSlider slider2;
      // End of variables declaration//GEN-END:variables
}
