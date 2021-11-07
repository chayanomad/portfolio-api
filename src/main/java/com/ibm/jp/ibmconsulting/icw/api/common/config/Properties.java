package com.ibm.jp.ibmconsulting.icw.api.common.config;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.convert.DefaultListDelimiterHandler;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.commons.configuration2.ex.ConfigurationRuntimeException;

public enum Properties {
  API_ERRORCODE("api_errorcode.properties", StandardCharsets.UTF_8.toString(), 2);

  private final PropertiesConfiguration config;
  private final int columns;

  private Properties(String filename, String encoding, int columns) {
    try {
      config =
          new FileBasedConfigurationBuilder<>(PropertiesConfiguration.class)
              .configure(
                  new Parameters()
                      .properties()
                      .setEncoding(encoding)
                      .setFile(new File(filename))
                      .setListDelimiterHandler(new DefaultListDelimiterHandler(',')))
              .getConfiguration();
      this.columns = columns;

    } catch (ConfigurationException e) {
      throw new ConfigurationRuntimeException("設定ファイル[%s]の初期化に失敗しました。", e);
    }
  }

  /**
   * キーと値の全てのペアを返す
   *
   * @return
   */
  public Map<String, String> get() {
    final Map<String, String> map = new HashMap<>();
    final Iterator<String> keys = config.getKeys();
    while (keys.hasNext()) {
      final String key = keys.next();
      final String value = String.join(",", config.getList(String.class, key));
      map.put(key, value);
    }
    return map;
  }

  /**
   * カンマ区切り形式で値が定義されているプロパティファイルにて、キーに対する値をリストとして返す.
   *
   * @param key キー
   * @return
   */
  public List<String> getValues(String key) {
    final List<String> values = config.getList(String.class, key);
    if (Objects.isNull(values)) {
      final String msg = "key[%s]に対するvalueが存在しません。";
      throw new ConfigurationRuntimeException(String.format(msg, key));
    }
    if (values.size() != columns) {
      final String msg = "key[%s]に対するvalueの形式が不正です。valueはカンマ区切りで%iつ存在する必要があります。";
      throw new ConfigurationRuntimeException(String.format(msg, key, columns));
    }
    return values;
  }

  /**
   * カンマ区切り形式で値が定義されているプロパティファイルにて、キーに対する値のうち指定した要素を返す.
   *
   * @param key キー
   * @param column 値の要素番号
   * @return
   */
  public String getValue(String key, int column) {
    final List<String> values = config.getList(String.class, key);
    if (Objects.isNull(values) || values.isEmpty()) {
      final String msg = "key[%s]に対するvalueが存在しません。";
      throw new IllegalArgumentException(String.format(msg, key));
    }
    if (values.size() < column) {
      final String msg = "key[%s]には%i番目の要素が存在しません。valueの要素数は%iです。";
      throw new ConfigurationRuntimeException(String.format(msg, key, column, columns));
    }
    return values.get(column);
  }

  /**
   * カンマ区切り形式で値が定義されているプロパティファイルにて、キーに対する値のうち指定した要素を返す.
   *
   * @param key キー
   * @param column 値の要素番号
   * @return
   */
  public int getValueInt(String key, int column) {
    final String value = getValue(key, column);
    if (Objects.isNull(value)) {
      final String msg = "key[%s]に対するvalueが存在しません。";
      throw new ConfigurationRuntimeException(String.format(msg, key));
    }
    try {
      return Integer.parseInt(value);
    } catch (NumberFormatException e) {
      final String msg = "key[%s]の%i番目の要素[%s]の数値変換に失敗しました。";
      throw new ConfigurationRuntimeException(String.format(msg, key, column, value), e);
    }
  }

  /**
   * キーに対する値を返す。カンマ区切りで値が定義されているプロパティファイルの場合は、0番目の要素を返す.
   *
   * @param key キー
   * @return
   */
  public String getValue(String key) {
    return getValue(key, 0);
  }

  /**
   * キーに対する値を返す。カンマ区切りで値が定義されているプロパティファイルの場合は、0番目の要素を返す.
   *
   * @param key キー
   * @return
   */
  public int getValueInt(String key) {
    return getValueInt(key, 0);
  }
}
