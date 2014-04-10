/*
 * Copyright 2014, Stratio.
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
package org.apache.cassandra.db.index.stratio;

import java.nio.ByteBuffer;

import org.apache.cassandra.config.CFMetaData;
import org.apache.cassandra.db.DecoratedKey;
import org.apache.cassandra.db.index.stratio.util.ByteBufferUtils;
import org.apache.cassandra.db.marshal.AbstractType;
import org.apache.cassandra.db.marshal.CompositeType;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.StringField;
import org.apache.lucene.index.Term;

public class FullKeyMapper {

	/** The Lucene's field name. */
	public static final String FIELD_NAME = "_full_key";

	public AbstractType<?> partitionKeyType;
	public AbstractType<?> clusteringKeyType;
	public CompositeType type;

	private FullKeyMapper(CFMetaData metadata) {
		this.partitionKeyType = metadata.getKeyValidator();
		this.clusteringKeyType = metadata.comparator;
		type = CompositeType.getInstance(partitionKeyType, clusteringKeyType);
	}

	public static FullKeyMapper instance(CFMetaData metadata) {
		return metadata.clusteringKeyColumns().size() > 0 ? new FullKeyMapper(metadata) : null;
	}

	public AbstractType<?> getPartitionKeyType() {
		return partitionKeyType;
	}

	public AbstractType<?> getClusteringKeyType() {
		return clusteringKeyType;
	}

	public CompositeType getType() {
		return type;
	}

	public void addFields(Document document, DecoratedKey partitionKey, ByteBuffer name) {
		ByteBuffer fullKey = type.builder().add(partitionKey.key).add(name).build();
		Field field = new StringField(FIELD_NAME, ByteBufferUtils.toString(fullKey), Store.YES);
		document.add(field);
	}

	public Term term(DecoratedKey partitionKey, ByteBuffer clusteringKey) {
		ByteBuffer fullKey = type.builder().add(partitionKey.key).add(clusteringKey).build();
		return new Term(FIELD_NAME, ByteBufferUtils.toString(fullKey));
	}

}
