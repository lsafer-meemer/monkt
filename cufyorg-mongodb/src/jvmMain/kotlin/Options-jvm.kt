/*
 *	Copyright 2022-2023 cufy.org
 *
 *	Licensed under the Apache License, Version 2.0 (the "License");
 *	you may not use this file except in compliance with the License.
 *	You may obtain a copy of the License at
 *
 *	    http://www.apache.org/licenses/LICENSE-2.0
 *
 *	Unless required by applicable law or agreed to in writing, software
 *	distributed under the License is distributed on an "AS IS" BASIS,
 *	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *	See the License for the specific language governing permissions and
 *	limitations under the License.
 */
package org.cufy.mongodb

import org.cufy.bson.java
import java.util.concurrent.TimeUnit

/* ============= ------------------ ============= */

/**
 * Return a java version of this.
 */
val DeleteOptions.java: JavaDeleteOptions
    get() {
        val options = JavaDeleteOptions()
            .collation(collation?.java)
            .hint(hint?.java)
            .hintString(hintString)
            .comment(comment?.java)
        let?.let { options.let(it.java) }
        return options
    }

/* ============= ------------------ ============= */

/**
 * Return a java version of this.
 */
val InsertOneOptions.java: JavaInsertOneOptions
    get() {
        return JavaInsertOneOptions()
            .bypassDocumentValidation(bypassDocumentValidation)
            .comment(comment?.java)
    }

/* ============= ------------------ ============= */

/**
 * Return a java version of this.
 */
val InsertManyOptions.java: JavaInsertManyOptions
    get() {
        return JavaInsertManyOptions()
            .ordered(ordered)
            .bypassDocumentValidation(bypassDocumentValidation)
            .comment(comment?.java)
    }

/* ============= ------------------ ============= */

/**
 * Return a java version of this.
 */
val UpdateOptions.java: JavaUpdateOptions
    get() {
        val options = JavaUpdateOptions()
            .upsert(upsert)
            .bypassDocumentValidation(bypassDocumentValidation)
            .collation(collation?.java)
            .arrayFilters(arrayFilters?.map { it.java })
            .hint(hint?.java)
            .hintString(hintString)
            .comment(comment?.java)
        let?.let { options.let(it.java) }
        return options
    }

/* ============= ------------------ ============= */

/**
 * Return a java version of this.
 */
val ReplaceOptions.java: JavaReplaceOptions
    get() {
        val options = JavaReplaceOptions()
            .upsert(upsert)
            .bypassDocumentValidation(bypassDocumentValidation)
            .collation(collation?.java)
            .hint(hint?.java)
            .hintString(hintString)
            .comment(comment?.java)
        let?.let { options.let(it.java) }
        return options
    }

/* ============= ------------------ ============= */

/**
 * Return a java version of this.
 */
val BulkWriteOptions.java: JavaBulkWriteOptions
    get() {
        val options = JavaBulkWriteOptions()
            .ordered(ordered)
            .bypassDocumentValidation(bypassDocumentValidation)
            .comment(comment?.java)
        let?.let { options.let(it.java) }
        return options
    }

/* ============= ------------------ ============= */

/**
 * Return a java version of this.
 */
val CountOptions.java: JavaCountOptions
    get() {
        return JavaCountOptions()
            .hint(hint?.java)
            .hintString(hintString)
            .limit(limit)
            .skip(skip)
            .maxTime(maxTime.inWholeMilliseconds, TimeUnit.MILLISECONDS)
            .collation(collation?.java)
            .comment(comment?.java)
    }

/* ============= ------------------ ============= */

/**
 * Return a java version of this.
 */
val EstimatedCountOptions.java: JavaEstimatedCountOptions
    get() {
        return JavaEstimatedCountOptions()
            .maxTime(maxTime.inWholeMilliseconds, TimeUnit.MILLISECONDS)
            .comment(comment?.java)
    }

/* ============= ------------------ ============= */

/**
 * Return a java version of this.
 */
val FindOneAndDeleteOptions.java: JavaFindOneAndDeleteOptions
    get() {
        val options = JavaFindOneAndDeleteOptions()
            .projection(projection?.java)
            .sort(sort?.java)
            .maxTime(maxTime.inWholeMilliseconds, TimeUnit.MILLISECONDS)
            .collation(collation?.java)
            .hint(hint?.java)
            .hintString(hintString)
            .comment(comment?.java)
        let?.let { options.let(it.java) }
        return options
    }

/* ============= ------------------ ============= */

/**
 * Return a java version of this.
 */
val FindOneAndReplaceOptions.java: JavaFindOneAndReplaceOptions
    get() {
        val options = JavaFindOneAndReplaceOptions()
            .projection(projection?.java)
            .sort(sort?.java)
            .upsert(upsert)
            .returnDocument(returnDocument.java)
            .maxTime(maxTime.inWholeMilliseconds, TimeUnit.MILLISECONDS)
            .bypassDocumentValidation(bypassDocumentValidation)
            .collation(collation?.java)
            .hint(hint?.java)
            .hintString(hintString)
            .comment(comment?.java)
        let?.let { options.let(it.java) }
        return options
    }

/* ============= ------------------ ============= */

/**
 * Return a java version of this.
 */
val FindOneAndUpdateOptions.java: JavaFindOneAndUpdateOptions
    get() {
        val options = JavaFindOneAndUpdateOptions()
            .projection(projection?.java)
            .sort(sort?.java)
            .upsert(upsert)
            .returnDocument(returnDocument.java)
            .maxTime(maxTime.inWholeMilliseconds, TimeUnit.MILLISECONDS)
            .bypassDocumentValidation(bypassDocumentValidation)
            .collation(collation?.java)
            .arrayFilters(arrayFilters?.map { it.java })
            .hint(hint?.java)
            .hintString(hintString)
            .comment(comment?.java)
        let?.let { options.let(it.java) }
        return options
    }

/* ============= ------------------ ============= */

/**
 * Apply the given [options] to this publisher.
 */
fun JavaFindPublisher.apply(options: FindOptions): JavaFindPublisher {
    limit(options.limit)
    skip(options.skip)
    maxTime(options.maxTime.inWholeMilliseconds, TimeUnit.MILLISECONDS)
    maxAwaitTime(options.maxAwaitTime.inWholeMilliseconds, TimeUnit.MILLISECONDS)
    projection(options.projection?.java)
    sort(options.sort?.java)
    noCursorTimeout(options.noCursorTimeout)
    partial(options.partial)
    cursorType(options.cursorType.java)
    collation(options.collation?.java)
    comment(options.comment?.java)
    hint(options.hint?.java)
    hintString(options.hintString)
    let(options.let?.java)
    max(options.max?.java)
    min(options.min?.java)
    returnKey(options.returnKey)
    showRecordId(options.showRecordId)
    options.batchSize?.let { batchSize(it) }
    allowDiskUse(options.allowDiskUse)
    return this
}

/* ============= ------------------ ============= */

/**
 * Apply the given [options] to this publisher.
 */
fun JavaAggregatePublisher.apply(options: AggregateOptions): JavaAggregatePublisher {
    allowDiskUse(options.allowDiskUse)
    maxTime(options.maxTime.inWholeMilliseconds, TimeUnit.MILLISECONDS)
    maxAwaitTime(options.maxAwaitTime.inWholeMilliseconds, TimeUnit.MILLISECONDS)
    bypassDocumentValidation(options.bypassDocumentValidation)
    collation(options.collation?.java)
    comment(options.comment?.java)
    hint(options.hint?.java)
    hintString(options.hintString)
    let(options.let?.java)
    options.batchSize?.let { batchSize(it) }
    return this
}

/* ============= ------------------ ============= */

/**
 * Apply the given [options] to this publisher.
 */
fun JavaDistinctPublisher.apply(options: DistinctOptions): JavaDistinctPublisher {
    maxTime(options.maxTime.inWholeMilliseconds, TimeUnit.MILLISECONDS)
    collation(options.collation?.java)
    options.batchSize?.let { batchSize(it) }
    comment(options.comment?.java)
    return this
}

/* ============= ------------------ ============= */

/**
 * Apply the given [options] to this publisher.
 */
fun JavaWatchPublisher.apply(options: WatchOptions): JavaWatchPublisher {
    fullDocument(options.fullDocument.java)
    options.resumeAfter?.java?.let { resumeAfter(it) }
    options.startAtOperationTime?.let { startAtOperationTime(org.bson.BsonTimestamp(it)) }
    options.startAfter?.java?.let { startAfter(it) }
    maxAwaitTime(options.maxAwaitTime.inWholeMilliseconds, TimeUnit.MILLISECONDS)
    collation(options.collation?.java)
    options.batchSize?.let { batchSize(it) }
    comment(options.comment?.java)
    showExpandedEvents(options.showExpandedEvents)
    return this
}

/* ============= ------------------ ============= */

/**
 * Apply the given [options] to this publisher.
 */
fun JavaListDatabasesPublisher.apply(options: ListDatabasesOptions): JavaListDatabasesPublisher {
    maxTime(options.maxTime.inWholeMilliseconds, TimeUnit.MILLISECONDS)
    nameOnly(options.nameOnly)
    authorizedDatabasesOnly(options.authorizedDatabasesOnly)
    options.batchSize?.let { batchSize(it) }
    comment(options.comment?.java)
    return this
}

/* ============= ------------------ ============= */

/**
 * Apply the given [options] to this publisher.
 */
fun JavaListCollectionsPublisher.apply(options: ListCollectionsOptions): JavaListCollectionsPublisher {
    maxTime(options.maxTime.inWholeMilliseconds, TimeUnit.MILLISECONDS)
    options.batchSize?.let { batchSize(it) }
    comment(options.comment?.java)
    return this
}

/* ============= ------------------ ============= */

/**
 * Apply the given [options] to this publisher.
 */
fun JavaListIndexesPublisher.apply(options: ListIndexesOptions): JavaListIndexesPublisher {
    maxTime(options.maxTime.inWholeMilliseconds, TimeUnit.MILLISECONDS)
    options.batchSize?.let { batchSize(it) }
    comment(options.comment?.java)
    return this
}

/* ============= ------------------ ============= */

/**
 * Return a java version of this.
 */
val CreateIndexOptions.java: JavaCreateIndexOptions
    get() {
        val options = JavaCreateIndexOptions()
            .background(background)
            .unique(unique)
            .name(name)
            .sparse(sparse)
            .version(version)
            .weights(weights?.java)
            .defaultLanguage(defaultLanguage)
            .languageOverride(languageOverride)
            .textVersion(textVersion)
            .sphereVersion(sphereVersion)
            .bits(bits)
            .min(min)
            .max(max)
            .storageEngine(storageEngine?.java)
            .partialFilterExpression(partialFilterExpression?.java)
            .collation(collation?.java)
            .hidden(hidden)
        expireAfter?.let { options.expireAfter(it.inWholeMilliseconds, TimeUnit.MILLISECONDS) }
        wildcardProjection?.let { options.wildcardProjection(it.java) }
        return options
    }

/* ============= ------------------ ============= */

/**
 * Return a java version of this.
 */
val CreateIndexesOptions.java: JavaCreateIndexesOptions
    get() {
        val options = JavaCreateIndexesOptions()
            .maxTime(maxTime.inWholeMilliseconds, TimeUnit.MILLISECONDS)
        commitQuorum?.let { options.commitQuorum(it.java) }
        return options
    }

/* ============= ------------------ ============= */

/**
 * Return a java version of this.
 */
val DropIndexOptions.java: JavaDropIndexOptions
    get() {
        return JavaDropIndexOptions()
            .maxTime(maxTime.inWholeMilliseconds, TimeUnit.MILLISECONDS)
    }

/* ============= ------------------ ============= */

/**
 * Return a java version of this.
 */
val CreateCollectionOptions.java: JavaCreateCollectionOptions
    get() {
        val options = JavaCreateCollectionOptions()
            .maxDocuments(maxDocuments)
            .capped(capped)
            .sizeInBytes(sizeInBytes)
            .storageEngineOptions(storageEngineOptions?.java)
            .validationOptions(validationOptions.java)
            .collation(collation?.java)
            .expireAfter(expireAfter.inWholeMilliseconds, TimeUnit.MILLISECONDS)
            .encryptedFields(encryptedFields?.java)

        indexOptionDefaults?.let { options.indexOptionDefaults(it.java) }
        timeSeriesOptions?.let { options.timeSeriesOptions(it.java) }
        clusteredIndexOptions?.let { options.clusteredIndexOptions(it.java) }
        changeStreamPreAndPostImagesOptions?.let {
            options.changeStreamPreAndPostImagesOptions(it.java)
        }
        return options
    }

/* ============= ------------------ ============= */

/**
 * Return a java version of this.
 */
val RenameCollectionOptions.java: JavaRenameCollectionOptions
    get() {
        return JavaRenameCollectionOptions()
            .dropTarget(dropTarget)
    }

/* ============= ------------------ ============= */

/**
 * Return a java version of this.
 */
val CreateViewOptions.java: JavaCreateViewOptions
    get() {
        return JavaCreateViewOptions()
            .collation(collation?.java)
    }

/* ============= ------------------ ============= */

/**
 * Return a java version of this.
 */
val ClientSessionOptions.java: JavaClientSessionOptions
    get() {
        val builder = JavaClientSessionOptions.builder()
            .defaultTransactionOptions(defaultTransactionOptions.java)
        causallyConsistent?.let { builder.causallyConsistent(it) }
        snapshot?.let { builder.snapshot(it) }
        return builder.build()
    }

/* ============= ------------------ ============= */

/**
 * Return a java version of this.
 */
val TextSearchOptions.java: JavaTextSearchOptions
    get() {
        val options = JavaTextSearchOptions()
        options.language(language)
        options.caseSensitive(caseSensitive)
        options.diacriticSensitive(diacriticSensitive)
        return options
    }

/* ============= ------------------ ============= */
