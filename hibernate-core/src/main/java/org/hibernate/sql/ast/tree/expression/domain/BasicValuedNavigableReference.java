/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later
 * See the lgpl.txt file in the root directory or http://www.gnu.org/licenses/lgpl-2.1.html
 */
package org.hibernate.sql.ast.tree.expression.domain;

import org.hibernate.metamodel.model.domain.spi.BasicValuedNavigable;
import org.hibernate.query.NavigablePath;
import org.hibernate.sql.ast.produce.spi.SqlAstCreationState;

/**
 * NavigableReference to a BasicValuedNavigable
 *
 * @author Steve Ebersole
 */
public class BasicValuedNavigableReference implements NavigableReference {
	private final NavigablePath navigablePath;
	private final BasicValuedNavigable referencedNavigable;

	public BasicValuedNavigableReference(
			NavigablePath navigablePath,
			BasicValuedNavigable referencedNavigable,
			@SuppressWarnings("unused") SqlAstCreationState creationState) {
		this.navigablePath = navigablePath;
		this.referencedNavigable = referencedNavigable;
	}

	@Override
	public BasicValuedNavigable getNavigable() {
		return referencedNavigable;
	}

	@Override
	public NavigablePath getNavigablePath() {
		return navigablePath;
	}
}