/*
 * Copyright 2014 Adam Dubiel, Przemek Hertel.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.smartparam.engine.report;

import org.smartparam.engine.report.tree.ReportingTreeLevelDescriptor;
import org.smartparam.engine.report.tree.ReportingTreeNode;
import org.smartparam.engine.report.tree.ReportingTreePath;
import org.smartparam.engine.report.tree.ReportLevelValuesSpace;
import java.util.*;
import org.smartparam.engine.matchers.type.Range;
import org.smartparam.engine.matchers.type.RangeBoundary;

/**
 *
 * @author Adam Dubiel
 */
public class ContinuousSegmentsSpace<C extends Comparable<? super C>, V> implements ReportLevelValuesSpace<V> {

    private final Set<ContinuousSpaceSegment<C, ReportingTreeNode<V>>> segments = new TreeSet<ContinuousSpaceSegment<C, ReportingTreeNode<V>>>();

    @Override
    public void uncheckedPut(Object key, ReportingTreeNode<V> node) {
        Range<C> range = asRange(key);
        segments.add(new ContinuousSpaceSegment<C, ReportingTreeNode<V>>(range.boundaryFrom(), range.boundaryTo(), node));
    }

    @SuppressWarnings("unchecked")
    private Range<C> asRange(Object object) {
        return (Range<C>) object;
    }

    @Override
    public boolean insertPath(Object key, ReportingTreePath<V> path, ReportingTreeLevelDescriptor levelDescriptor) {
        Range<C> range = asRange(key);
        return insertPath(range.boundaryFrom(), range.boundaryTo(), path, levelDescriptor);
    }

    private boolean insertPath(RangeBoundary<C> from, RangeBoundary<C> to, ReportingTreePath<V> path, ReportingTreeLevelDescriptor levelDescriptor) {
        List<ContinuousSpaceSegment<C, ReportingTreeNode<V>>> refreshed = new ArrayList<ContinuousSpaceSegment<C, ReportingTreeNode<V>>>();
        boolean pathAdded = false;

        for (ContinuousSpaceSegment<C, ReportingTreeNode<V>> segment : segments) {
            ContinuousSpaceSegment.IntersectionType intersection = segment.intersects(from, to);
            pathAdded = pathAdded || intersection != ContinuousSpaceSegment.IntersectionType.NONE;

            if (intersection == ContinuousSpaceSegment.IntersectionType.NONE) {
                refreshed.add(segment);
            } else if (intersection == ContinuousSpaceSegment.IntersectionType.IDENTICAL) {
                segment.value().insertPath(path);
                refreshed.add(segment);
            } else if (intersection == ContinuousSpaceSegment.IntersectionType.CONTAINS) {
                refreshed.add(segment(segment.segmentStart(), from, segment.value(), levelDescriptor));
                refreshed.add(segment(from, to, segment.value(), path, levelDescriptor));
                refreshed.add(segment(to, segment.segmentEnd(), segment.value(), levelDescriptor));
            } else if (intersection == ContinuousSpaceSegment.IntersectionType.BEFORE) {
                refreshed.add(segment(from, segment.segmentStart(), segment.value(), levelDescriptor));
                refreshed.add(segment(segment.segmentStart(), to, segment.value(), path, levelDescriptor));
            } else if (intersection == ContinuousSpaceSegment.IntersectionType.AFTER) {
                refreshed.add(segment(from, segment.segmentEnd(), segment.value(), levelDescriptor));
                refreshed.add(segment(segment.segmentEnd(), to, segment.value(), path, levelDescriptor));
            }
        }

        segments.clear();
        segments.addAll(refreshed);

        return pathAdded;
    }

    private ContinuousSpaceSegment<C, ReportingTreeNode<V>> segment(RangeBoundary<C> from, RangeBoundary<C> to, ReportingTreeNode<V> toClone, ReportingTreeLevelDescriptor levelDescriptor) {
        ReportingTreeNode<V> clone = toClone.cloneBranch(toClone.parent());
        clone.updateLevelValue(levelDescriptor.encode(new Range<C>(from, to)));

        return new ContinuousSpaceSegment<C, ReportingTreeNode<V>>(from, to, clone);
    }

    private ContinuousSpaceSegment<C, ReportingTreeNode<V>> segment(RangeBoundary<C> from, RangeBoundary<C> to, ReportingTreeNode<V> toClone, ReportingTreePath<V> path, ReportingTreeLevelDescriptor levelDescriptor) {
        ReportingTreeNode<V> clone = toClone.cloneBranch(toClone.parent());
        clone.updateLevelValue(levelDescriptor.encode(new Range<C>(from, to)));
        clone.insertPath(path);

        return new ContinuousSpaceSegment<C, ReportingTreeNode<V>>(from, to, clone);
    }

    @Override
    public Iterable<ReportingTreeNode<V>> values() {
        return new Iterable<ReportingTreeNode<V>>() {

            @Override
            public Iterator<ReportingTreeNode<V>> iterator() {
                return new Iterator<ReportingTreeNode<V>>() {

                    private final Iterator<ContinuousSpaceSegment<C, ReportingTreeNode<V>>> iterator = segments.iterator();

                    @Override
                    public boolean hasNext() {
                        return iterator.hasNext();
                    }

                    @Override
                    public ReportingTreeNode<V> next() {
                        return iterator.next().value();
                    }

                    @Override
                    public void remove() {
                        iterator.remove();
                    }
                };

            }
        };
    }

    @Override
    public boolean empty() {
        return segments.isEmpty();
    }

    @Override
    public ReportLevelValuesSpace<V> cloneSpace(ReportingTreeNode<V> newParent) {
        ContinuousSegmentsSpace<C, V> clone = new ContinuousSegmentsSpace<C, V>();
        for (ContinuousSpaceSegment<C, ReportingTreeNode<V>> segment : segments) {
            ContinuousSpaceSegment<C, ReportingTreeNode<V>> cloneSegment = new ContinuousSpaceSegment<C, ReportingTreeNode<V>>(segment, segment.value().cloneBranch(newParent));
            clone.segments.add(cloneSegment);
        }

        return clone;
    }
}