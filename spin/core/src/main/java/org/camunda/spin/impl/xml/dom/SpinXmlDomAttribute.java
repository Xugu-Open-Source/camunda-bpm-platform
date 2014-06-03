/* Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.camunda.spin.impl.xml.dom;

import org.camunda.spin.logging.SpinLogger;
import org.camunda.spin.xml.tree.SpinXmlTreeAttribute;
import org.camunda.spin.xml.tree.SpinXmlTreeElement;
import org.w3c.dom.Attr;
import org.w3c.dom.Element;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.nio.charset.Charset;

/**
 * Wrapper of a xml dom attribute.
 *
 * @author Sebastian Menski
 */
public class SpinXmlDomAttribute extends SpinXmlTreeAttribute {

  private final static XmlDomLogger LOG = SpinLogger.XML_DOM_LOGGER;

  protected final Attr attributeNode;

  protected final XmlDomDataFormat dataFormat;

  /**
   * Create a new wrapper.
   *
   * @param attributeNode the dom xml attribute to wrap
   * @param dataFormat the xml dom data format
   */
  public SpinXmlDomAttribute(Attr attributeNode, XmlDomDataFormat dataFormat) {
    this.attributeNode = attributeNode;
    this.dataFormat = dataFormat;
  }

  public String getDataFormatName() {
    return dataFormat.getName();
  }

  public Attr unwrap() {
    return attributeNode;
  }

  public String name() {
    return attributeNode.getLocalName();
  }

  public String namespace() {
    return attributeNode.getNamespaceURI();
  }

  public boolean hasNamespace(String namespace) {
    String attributeNamespace = attributeNode.getNamespaceURI();
    if (attributeNamespace == null) {
      return namespace == null;
    }
    else {
      return attributeNamespace.equals(namespace);
    }
  }

  public String value() {
    return attributeNode.getValue();
  }

  public SpinXmlTreeAttribute value(String value) {
    if (value == null) {
      throw LOG.unableToSetAttributeValueToNull(namespace(), name());
    }
    attributeNode.setValue(value);
    return this;
  }

  public SpinXmlTreeElement remove() {
    Element ownerElement = attributeNode.getOwnerElement();
    ownerElement.removeAttributeNode(attributeNode);
    return dataFormat.createElementWrapper(ownerElement);
  }

  public String toString() {
    return value();
  }

  public OutputStream toStream() {
    return writeToStream(new ByteArrayOutputStream());
  }

  public <T extends OutputStream> T writeToStream(T outputStream) {
    byte[] bytes = toString().getBytes(Charset.forName("UTF-8"));
    try {
      outputStream.write(bytes);
    } catch (IOException e) {
      throw LOG.unableToWriteAttribute(this, e);
    }
    return outputStream;
  }

  public <T extends Writer> T writeToWriter(T writer) {
    try {
      writer.write(toString());
    } catch (IOException e) {
      throw LOG.unableToWriteAttribute(this, e);
    }
    return writer;
  }

}
