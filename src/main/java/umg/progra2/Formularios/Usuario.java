package umg.progra2.Formularios;
import umg.progra2.DataBase.Model.UsuarioModel;
import umg.progra2.DataBase.Service.UsuarioService;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Usuario extends JFrame {
    private JPanel Form2;
    private JLabel lblID;
    private JLabel lblNombre;
    private JLabel lblCarnet;
    private JLabel lblCorreo;
    private JTextField textFieldID;
    private JTextField textFieldNombre;
    private JTextField textFieldCarnet;
    private JTextField textFieldCorreo;
    private JButton buttonAgregar;
    private JButton buttonBuscar;
    private JButton buttonActualizar;
    private JButton buttonEliminar;

    private UsuarioService usuarioService = new UsuarioService();

    public Usuario() {
        setContentPane(Form2);
        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        buttonAgregar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UsuarioModel usuario = new UsuarioModel();
                usuario.setNombre(textFieldNombre.getText());
                usuario.setCarne(textFieldCarnet.getText());
                usuario.setCorreo(textFieldCorreo.getText());
                try {
                    if (usuarioService.checkEmailDuplicated(usuario.getCorreo())) {
                        JOptionPane.showMessageDialog(null, "El correo ya se encuentra en uso Por favor, utiliza otro correo.");
                        return;
                        //Si el correo existe, salir.
                    }
                    usuarioService.createUser(usuario);
                    JOptionPane.showMessageDialog(null, "El nuevo usuario a sido creado exitosamente.");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error al crear el usuario:  " + ex.getMessage());
                    ex.printStackTrace();
                }

            }
        });

        buttonActualizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int id = Integer.parseInt(textFieldID.getText());
                    String nombre = textFieldNombre.getText();
                    String carnet = textFieldCarnet.getText();
                    String correo = textFieldCorreo.getText();
                    UsuarioModel usuario = new UsuarioModel(id, carnet, nombre, correo, "");

                    boolean actualizado = usuarioService.actualizarUser(usuario);

                    if (actualizado) {
                        JOptionPane.showMessageDialog(null, "El usuario se a actualizado exitosamente");
                    } else {
                        JOptionPane.showMessageDialog(null, "No se pudo actualizar el usuario");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Error: El ID debe ser un numero valido.");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error al actualizar usuario: " + ex.getMessage());
                    ex.printStackTrace();
                }
            }
        });

        buttonEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String idText = textFieldID.getText();

                    if (idText.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Ingrese un ID para eliminarlo del registro.");
                        return;
                    }

                    int id = Integer.parseInt(idText);

                    int confirmar = JOptionPane.showConfirmDialog(
                            null,
                            "¿Estas seguro de que desea eliminar el registro con el ID " + id + "?",
                            "Confirmar Eliminacion",
                            JOptionPane.YES_NO_OPTION
                    );

                    if (confirmar == JOptionPane.YES_OPTION) {
                        boolean eliminado = usuarioService.eliminarUser(id);

                        if (eliminado) {
                            JOptionPane.showMessageDialog(null, "Usuario eliminado exitosamente.");
                            limpiarCampos();
                        } else {
                            JOptionPane.showMessageDialog(null, "No se pudo eliminar el usuario.");
                        }
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Error: El ID debe ser un numero valido.");
                } catch (Exception ex) {

                    JOptionPane.showMessageDialog(null, "Error al eliminar el usuario: " + ex.getMessage());
                    ex.printStackTrace();
                }

            }
        });


        buttonBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String carnetUsuario = textFieldCarnet.getText().isEmpty() ? null : textFieldCarnet.getText();
                try {
                    UsuarioModel usuarioEncontrado = usuarioService.getUserByCarne(carnetUsuario);
                    if (usuarioEncontrado != null) {
                        textFieldID.setText(Integer.toString(usuarioEncontrado.getId()));
                        textFieldNombre.setText(usuarioEncontrado.getNombre());
                        textFieldCarnet.setText(usuarioEncontrado.getCarne());
                        textFieldCorreo.setText(usuarioEncontrado.getCorreo());
                    } else {
                        JOptionPane.showMessageDialog(null, "No se encontro el usuario.");
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error al obtener el usuario: " + ex.getMessage());
                    ex.printStackTrace();
                }
            }
        });

    }
    private void limpiarCampos() {
        textFieldID.setText("");
        textFieldNombre.setText("");
        textFieldCarnet.setText("");
        textFieldCorreo.setText("");
    }
}
