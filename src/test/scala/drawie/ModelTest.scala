package drawie

import org.scalamock.scalatest.MockFactory
import org.scalatest.{FlatSpec, Matchers}

class ModelTest extends FlatSpec with Matchers with MockFactory {

  "joinRoom" should "return false when url length is zero" in {
    Model.joinRoom("") should be (false)
  }
}
