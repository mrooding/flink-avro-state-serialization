package nl.mrooding.data

import java.time.Instant

case class ProductStock(id: String,
                        stock: Long,
                        updatedAt: Instant)
