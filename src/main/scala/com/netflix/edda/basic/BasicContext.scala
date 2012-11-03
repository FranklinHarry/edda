/**
 * Copyright 2012 Netflix, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.netflix.edda.basic

import com.netflix.edda.Collection

import java.util.Properties

import org.slf4j.LoggerFactory

/** Simple collection context that loads /edda.properties to use
  * as the configuration.  It also uses the BasicRecordMatcher as
  * the recordMatcher
  */
object BasicContext extends Collection.Context {
  private[this] val logger = LoggerFactory.getLogger(getClass)
  val propFile = System.getProperty("edda.properties", "/edda.properties")
  val props = new Properties()
  try {
    val inputStream = getClass.getResourceAsStream(propFile)
    try {
      props.load(inputStream)
    } finally {
      inputStream.close()
    }
  } catch {
    case e: Exception =>
      logger.error("Unable to load properties file " + propFile
        + " set System property \"edda.properties\" to valid file", e)
  }
  val config = props

  lazy val recordMatcher = new BasicRecordMatcher
}
