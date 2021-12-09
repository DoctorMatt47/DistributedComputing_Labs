package com.distributed.client;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class Ui extends JPanel {
    private static final int WIDTH = 500;
    private static final int HEIGHT = 400;
    private final TextArea input;
    private final TextArea output;
    private final JComboBox sourceSelect;
    private final JComboBox entitySelect;
    private final JComboBox operationSelect;
    private final JComboBox connectionSelect;
    private final JButton sendButton;
    private final RequestSender sender;

    public Ui(RequestSender sender) {
        this.sender = sender;
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setLayout(new FlowLayout());
        input = new TextArea();
        output = new TextArea();
        sourceSelect = createSourceSelect();
        entitySelect = createEntitySelect();
        operationSelect = createOperationSelect();
        sendButton = createSendButton();
        connectionSelect = createConnectionSelect();
        add(input);
        add(output);
        add(sourceSelect);
        add(entitySelect);
        add(operationSelect);
        add(connectionSelect);
        add(sendButton);
    }

    private JComboBox createSourceSelect() {
        var sources = new String[] {"XML", "DB"};
        return new JComboBox(sources);
    }

    private JComboBox createEntitySelect() {
        var entities = new String[] {"Producer", "Product"};
        return new JComboBox(entities);
    }

    private JComboBox createOperationSelect() {
        var operations = new String[] {"Get all", "Get by id", "Add", "Update", "Delete"};
        return new JComboBox(operations);
    }

    private JComboBox createConnectionSelect() {
        var connections = new String[] {"Socket"};
        return new JComboBox(connections);
    }

    private JButton createSendButton() {
        var btn = new JButton("Send");
        btn.addActionListener(e -> {
            try {
                send();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        return btn;
    }

    private void send() throws IOException {
        var output = sender.send(sourceSelect.getSelectedIndex(),
                entitySelect.getSelectedIndex(),
                operationSelect.getSelectedIndex(),
                connectionSelect.getSelectedIndex(),
                input.getText());
        this.output.setText(output);
    }
}
