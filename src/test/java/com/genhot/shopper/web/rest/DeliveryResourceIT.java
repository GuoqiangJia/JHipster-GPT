package com.genhot.shopper.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;

import com.genhot.shopper.IntegrationTest;
import com.genhot.shopper.domain.Delivery;
import com.genhot.shopper.repository.DeliveryRepository;
import com.genhot.shopper.repository.EntityManager;
import com.genhot.shopper.service.dto.DeliveryDTO;
import com.genhot.shopper.service.mapper.DeliveryMapper;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.reactive.server.WebTestClient;

/**
 * Integration tests for the {@link DeliveryResource} REST controller.
 */
@IntegrationTest
@AutoConfigureWebTestClient(timeout = IntegrationTest.DEFAULT_ENTITY_TIMEOUT)
@WithMockUser
class DeliveryResourceIT {

    private static final String DEFAULT_TRACKING_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_TRACKING_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_CARRIER = "AAAAAAAAAA";
    private static final String UPDATED_CARRIER = "BBBBBBBBBB";

    private static final Instant DEFAULT_SHIPPING_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_SHIPPING_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/deliveries";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DeliveryRepository deliveryRepository;

    @Autowired
    private DeliveryMapper deliveryMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private WebTestClient webTestClient;

    private Delivery delivery;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Delivery createEntity(EntityManager em) {
        Delivery delivery = new Delivery()
            .trackingNumber(DEFAULT_TRACKING_NUMBER)
            .carrier(DEFAULT_CARRIER)
            .shippingDate(DEFAULT_SHIPPING_DATE);
        return delivery;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Delivery createUpdatedEntity(EntityManager em) {
        Delivery delivery = new Delivery()
            .trackingNumber(UPDATED_TRACKING_NUMBER)
            .carrier(UPDATED_CARRIER)
            .shippingDate(UPDATED_SHIPPING_DATE);
        return delivery;
    }

    public static void deleteEntities(EntityManager em) {
        try {
            em.deleteAll(Delivery.class).block();
        } catch (Exception e) {
            // It can fail, if other entities are still referring this - it will be removed later.
        }
    }

    @AfterEach
    public void cleanup() {
        deleteEntities(em);
    }

    @BeforeEach
    public void initTest() {
        deleteEntities(em);
        delivery = createEntity(em);
    }

    @Test
    void createDelivery() throws Exception {
        int databaseSizeBeforeCreate = deliveryRepository.findAll().collectList().block().size();
        // Create the Delivery
        DeliveryDTO deliveryDTO = deliveryMapper.toDto(delivery);
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(deliveryDTO))
            .exchange()
            .expectStatus()
            .isCreated();

        // Validate the Delivery in the database
        List<Delivery> deliveryList = deliveryRepository.findAll().collectList().block();
        assertThat(deliveryList).hasSize(databaseSizeBeforeCreate + 1);
        Delivery testDelivery = deliveryList.get(deliveryList.size() - 1);
        assertThat(testDelivery.getTrackingNumber()).isEqualTo(DEFAULT_TRACKING_NUMBER);
        assertThat(testDelivery.getCarrier()).isEqualTo(DEFAULT_CARRIER);
        assertThat(testDelivery.getShippingDate()).isEqualTo(DEFAULT_SHIPPING_DATE);
    }

    @Test
    void createDeliveryWithExistingId() throws Exception {
        // Create the Delivery with an existing ID
        delivery.setId(1L);
        DeliveryDTO deliveryDTO = deliveryMapper.toDto(delivery);

        int databaseSizeBeforeCreate = deliveryRepository.findAll().collectList().block().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(deliveryDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Delivery in the database
        List<Delivery> deliveryList = deliveryRepository.findAll().collectList().block();
        assertThat(deliveryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void checkTrackingNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = deliveryRepository.findAll().collectList().block().size();
        // set the field null
        delivery.setTrackingNumber(null);

        // Create the Delivery, which fails.
        DeliveryDTO deliveryDTO = deliveryMapper.toDto(delivery);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(deliveryDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<Delivery> deliveryList = deliveryRepository.findAll().collectList().block();
        assertThat(deliveryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkCarrierIsRequired() throws Exception {
        int databaseSizeBeforeTest = deliveryRepository.findAll().collectList().block().size();
        // set the field null
        delivery.setCarrier(null);

        // Create the Delivery, which fails.
        DeliveryDTO deliveryDTO = deliveryMapper.toDto(delivery);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(deliveryDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<Delivery> deliveryList = deliveryRepository.findAll().collectList().block();
        assertThat(deliveryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkShippingDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = deliveryRepository.findAll().collectList().block().size();
        // set the field null
        delivery.setShippingDate(null);

        // Create the Delivery, which fails.
        DeliveryDTO deliveryDTO = deliveryMapper.toDto(delivery);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(deliveryDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<Delivery> deliveryList = deliveryRepository.findAll().collectList().block();
        assertThat(deliveryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void getAllDeliveries() {
        // Initialize the database
        deliveryRepository.save(delivery).block();

        // Get all the deliveryList
        webTestClient
            .get()
            .uri(ENTITY_API_URL + "?sort=id,desc")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.[*].id")
            .value(hasItem(delivery.getId().intValue()))
            .jsonPath("$.[*].trackingNumber")
            .value(hasItem(DEFAULT_TRACKING_NUMBER))
            .jsonPath("$.[*].carrier")
            .value(hasItem(DEFAULT_CARRIER))
            .jsonPath("$.[*].shippingDate")
            .value(hasItem(DEFAULT_SHIPPING_DATE.toString()));
    }

    @Test
    void getDelivery() {
        // Initialize the database
        deliveryRepository.save(delivery).block();

        // Get the delivery
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, delivery.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.id")
            .value(is(delivery.getId().intValue()))
            .jsonPath("$.trackingNumber")
            .value(is(DEFAULT_TRACKING_NUMBER))
            .jsonPath("$.carrier")
            .value(is(DEFAULT_CARRIER))
            .jsonPath("$.shippingDate")
            .value(is(DEFAULT_SHIPPING_DATE.toString()));
    }

    @Test
    void getNonExistingDelivery() {
        // Get the delivery
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, Long.MAX_VALUE)
            .accept(MediaType.APPLICATION_PROBLEM_JSON)
            .exchange()
            .expectStatus()
            .isNotFound();
    }

    @Test
    void putExistingDelivery() throws Exception {
        // Initialize the database
        deliveryRepository.save(delivery).block();

        int databaseSizeBeforeUpdate = deliveryRepository.findAll().collectList().block().size();

        // Update the delivery
        Delivery updatedDelivery = deliveryRepository.findById(delivery.getId()).block();
        updatedDelivery.trackingNumber(UPDATED_TRACKING_NUMBER).carrier(UPDATED_CARRIER).shippingDate(UPDATED_SHIPPING_DATE);
        DeliveryDTO deliveryDTO = deliveryMapper.toDto(updatedDelivery);

        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, deliveryDTO.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(deliveryDTO))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Delivery in the database
        List<Delivery> deliveryList = deliveryRepository.findAll().collectList().block();
        assertThat(deliveryList).hasSize(databaseSizeBeforeUpdate);
        Delivery testDelivery = deliveryList.get(deliveryList.size() - 1);
        assertThat(testDelivery.getTrackingNumber()).isEqualTo(UPDATED_TRACKING_NUMBER);
        assertThat(testDelivery.getCarrier()).isEqualTo(UPDATED_CARRIER);
        assertThat(testDelivery.getShippingDate()).isEqualTo(UPDATED_SHIPPING_DATE);
    }

    @Test
    void putNonExistingDelivery() throws Exception {
        int databaseSizeBeforeUpdate = deliveryRepository.findAll().collectList().block().size();
        delivery.setId(count.incrementAndGet());

        // Create the Delivery
        DeliveryDTO deliveryDTO = deliveryMapper.toDto(delivery);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, deliveryDTO.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(deliveryDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Delivery in the database
        List<Delivery> deliveryList = deliveryRepository.findAll().collectList().block();
        assertThat(deliveryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchDelivery() throws Exception {
        int databaseSizeBeforeUpdate = deliveryRepository.findAll().collectList().block().size();
        delivery.setId(count.incrementAndGet());

        // Create the Delivery
        DeliveryDTO deliveryDTO = deliveryMapper.toDto(delivery);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, count.incrementAndGet())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(deliveryDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Delivery in the database
        List<Delivery> deliveryList = deliveryRepository.findAll().collectList().block();
        assertThat(deliveryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamDelivery() throws Exception {
        int databaseSizeBeforeUpdate = deliveryRepository.findAll().collectList().block().size();
        delivery.setId(count.incrementAndGet());

        // Create the Delivery
        DeliveryDTO deliveryDTO = deliveryMapper.toDto(delivery);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(deliveryDTO))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the Delivery in the database
        List<Delivery> deliveryList = deliveryRepository.findAll().collectList().block();
        assertThat(deliveryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateDeliveryWithPatch() throws Exception {
        // Initialize the database
        deliveryRepository.save(delivery).block();

        int databaseSizeBeforeUpdate = deliveryRepository.findAll().collectList().block().size();

        // Update the delivery using partial update
        Delivery partialUpdatedDelivery = new Delivery();
        partialUpdatedDelivery.setId(delivery.getId());

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedDelivery.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedDelivery))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Delivery in the database
        List<Delivery> deliveryList = deliveryRepository.findAll().collectList().block();
        assertThat(deliveryList).hasSize(databaseSizeBeforeUpdate);
        Delivery testDelivery = deliveryList.get(deliveryList.size() - 1);
        assertThat(testDelivery.getTrackingNumber()).isEqualTo(DEFAULT_TRACKING_NUMBER);
        assertThat(testDelivery.getCarrier()).isEqualTo(DEFAULT_CARRIER);
        assertThat(testDelivery.getShippingDate()).isEqualTo(DEFAULT_SHIPPING_DATE);
    }

    @Test
    void fullUpdateDeliveryWithPatch() throws Exception {
        // Initialize the database
        deliveryRepository.save(delivery).block();

        int databaseSizeBeforeUpdate = deliveryRepository.findAll().collectList().block().size();

        // Update the delivery using partial update
        Delivery partialUpdatedDelivery = new Delivery();
        partialUpdatedDelivery.setId(delivery.getId());

        partialUpdatedDelivery.trackingNumber(UPDATED_TRACKING_NUMBER).carrier(UPDATED_CARRIER).shippingDate(UPDATED_SHIPPING_DATE);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedDelivery.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedDelivery))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Delivery in the database
        List<Delivery> deliveryList = deliveryRepository.findAll().collectList().block();
        assertThat(deliveryList).hasSize(databaseSizeBeforeUpdate);
        Delivery testDelivery = deliveryList.get(deliveryList.size() - 1);
        assertThat(testDelivery.getTrackingNumber()).isEqualTo(UPDATED_TRACKING_NUMBER);
        assertThat(testDelivery.getCarrier()).isEqualTo(UPDATED_CARRIER);
        assertThat(testDelivery.getShippingDate()).isEqualTo(UPDATED_SHIPPING_DATE);
    }

    @Test
    void patchNonExistingDelivery() throws Exception {
        int databaseSizeBeforeUpdate = deliveryRepository.findAll().collectList().block().size();
        delivery.setId(count.incrementAndGet());

        // Create the Delivery
        DeliveryDTO deliveryDTO = deliveryMapper.toDto(delivery);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, deliveryDTO.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(deliveryDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Delivery in the database
        List<Delivery> deliveryList = deliveryRepository.findAll().collectList().block();
        assertThat(deliveryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchDelivery() throws Exception {
        int databaseSizeBeforeUpdate = deliveryRepository.findAll().collectList().block().size();
        delivery.setId(count.incrementAndGet());

        // Create the Delivery
        DeliveryDTO deliveryDTO = deliveryMapper.toDto(delivery);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, count.incrementAndGet())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(deliveryDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Delivery in the database
        List<Delivery> deliveryList = deliveryRepository.findAll().collectList().block();
        assertThat(deliveryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamDelivery() throws Exception {
        int databaseSizeBeforeUpdate = deliveryRepository.findAll().collectList().block().size();
        delivery.setId(count.incrementAndGet());

        // Create the Delivery
        DeliveryDTO deliveryDTO = deliveryMapper.toDto(delivery);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(deliveryDTO))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the Delivery in the database
        List<Delivery> deliveryList = deliveryRepository.findAll().collectList().block();
        assertThat(deliveryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteDelivery() {
        // Initialize the database
        deliveryRepository.save(delivery).block();

        int databaseSizeBeforeDelete = deliveryRepository.findAll().collectList().block().size();

        // Delete the delivery
        webTestClient
            .delete()
            .uri(ENTITY_API_URL_ID, delivery.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNoContent();

        // Validate the database contains one less item
        List<Delivery> deliveryList = deliveryRepository.findAll().collectList().block();
        assertThat(deliveryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
