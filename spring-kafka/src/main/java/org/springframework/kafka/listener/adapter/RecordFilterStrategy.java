/*
 * Copyright 2016-2021 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.kafka.listener.adapter;

import java.util.Iterator;
import java.util.List;

import org.apache.kafka.clients.consumer.ConsumerRecord;

/**
 * Implementations of this interface can signal that a record about
 * to be delivered to a message listener should be discarded instead
 * of being delivered.
 *
 * @param <K> the key type.
 * @param <V> the value type.
 *
 * @author Gary Russell
 *
 */
public interface RecordFilterStrategy<K, V> {

	/**
	 * Return true if the record should be discarded.
	 * @param consumerRecord the record.
	 * @return true to discard.
	 */
	boolean filter(ConsumerRecord<K, V> consumerRecord);

	/**
	 * Filter an entire batch of records; to filter all records, return an empty list, not
	 * null.
	 * @param records the records.
	 * @return the filtered records.
	 * @since 2.8
	 */
	default List<ConsumerRecord<K, V>> filterBatch(List<ConsumerRecord<K, V>> records) {
		Iterator<ConsumerRecord<K, V>> iterator = records.iterator();
		while (iterator.hasNext()) {
			if (filter(iterator.next())) {
				iterator.remove();
			}
		}
		return records;
	}

}
