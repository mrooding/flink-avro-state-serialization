package data

import java.time.Instant

case class ProductStock(id: String,
                        stock: Long,
                        updatedAt: Instant)
