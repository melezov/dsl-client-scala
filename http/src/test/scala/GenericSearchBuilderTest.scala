import com.dslplatform.api.client.GenericSearchBuilder
import com.dslplatform.api.patterns.{PersistableRepository, ServiceLocator}

import org.specs2._
import org.junit.runner.RunWith
import org.specs2.runner.JUnitRunner
import com.dslplatform.test.simple._
import org.specs2.specification.Step

@RunWith(classOf[JUnitRunner])
class GenericSearchBuilderTest extends Specification with Common {

  def is = sequential ^ s2"""
    Domain Proxy is used to find domain objects and submit events over the remote service
      insert Roots              ${located(insertRoots)}
      search generic            ${located(searchGeneric)}
                                ${Step(located.close())}
  """

  private val located = new located {}

  private val numOfRoots = 27
  private val simpleRoots = for (i <- 1 to numOfRoots) yield SimpleRoot(rInt, rFloat, rName)

  def insertRoots = { locator: ServiceLocator =>
    await(locator.resolve[PersistableRepository[SimpleRoot]].insert(simpleRoots))
    success
  }

  def searchGeneric = { implicit locator: ServiceLocator =>
    val greaterThan = 20
    val search = new GenericSearchBuilder[SimpleRoot]().greaterThen("i", greaterThan).search
    //search.map(_.map(_.i)) must  (greaterThan).await
    pending

  }
}
