/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

/*
 * Example Sampler GUI (non-beans version)
 */

package com.boa.jmeter.reporters;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.apache.jmeter.samplers.gui.AbstractSamplerGui;
import org.apache.jmeter.testelement.TestElement;
import org.apache.jmeter.util.JMeterUtils;

/**
 * Placeholder GUI class for MySQLResultsSaver
 *
 */
public class MySQLResultsSaverGui extends AbstractSamplerGui {

    private static final long serialVersionUID = 240L;

    private JTextArea data;

    public MySQLResultsSaverGui() {
        init();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getLabelResource() {
        return "mysql_title"; // $NON-NLS-1$
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void configure(TestElement element) {
        data.setText("FOO"); //element.getPropertyAsString(ExampleSampler.DATA));
        super.configure(element);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TestElement createTestElement() {
        MySQLResultsSaver sampler = new MySQLResultsSaver();
        modifyTestElement(sampler);
        return sampler;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void modifyTestElement(TestElement te) {
        te.clear();
        configureTestElement(te);
        te.setProperty(MySQLResultsSaver.DATA, data.getText());
    }

    /*
     * Helper method to set up the GUI screen
     */
    private void init() {
        // Standard setup
        setLayout(new BorderLayout(0, 5));
        setBorder(makeBorder());
        //add(makeTitlePanel(), BorderLayout.NORTH); // Add the standard title

        // Specific setup
        add(createDataPanel(), BorderLayout.CENTER);
    }

    /*
     * Create a data input text field
     *
     * @return the panel for entering the data
     */
    private Component createDataPanel() {
        //JLabel label = new JLabel(JMeterUtils.getResString("Foo_Data")); //"example_data")); //$NON-NLS-1$

        //data = new JTextArea();
        //data.setName(MySQLResultsSaver.DATA);
        //label.setLabelFor(data);
        JLabel iterationLabel = new JLabel("Iteration");
        JTextField iterationField = new JTextField(5);

        JPanel dataPanel = new JPanel(new BorderLayout(5, 0));
        dataPanel.add(iterationLabel, BorderLayout.WEST);
        dataPanel.add(iterationField);
        //dataPanel.add(data, BorderLayout.CENTER);

        return dataPanel;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void clearGui() {
        super.clearGui();
        data.setText(""); // $NON-NLS-1$

    }
}
