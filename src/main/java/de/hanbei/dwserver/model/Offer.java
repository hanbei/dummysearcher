package de.hanbei.dwserver.model;

import java.util.List;

/**
 * Created by hanbei on 3/24/16.
 */
public class Offer {

    private final String merchant;
    private final String imageUrl;
    private final String url;
    private final String merchantImageUrl;
    private final String currency;
    private final String id;
    private final String category;
    private final String title;
    private final String description;
    private final Double price;
    private final String manufacturer;
    private final String merchantName;
    private final String offerSource;
    private final Double estimatedCPC;
    private final String availability;
    private final Double deliveryCost;
    private final List<String> ean;
    private String source;

    public Offer() {
        this(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
    }

    public Offer(String merchant, String imageUrl, String url, String merchantImageUrl, String currency, String id, String category, String title, String description, Double price, String manufacturer, String merchantName, String offerSource, Double estimatedCPC, String availability, Double deliveryCost, List<String> ean) {
        this.merchant = merchant;
        this.imageUrl = imageUrl;
        this.url = url;
        this.merchantImageUrl = merchantImageUrl;
        this.currency = currency;
        this.id = id;
        this.category = category;
        this.title = title;
        this.description = description;
        this.price = price;
        this.manufacturer = manufacturer;
        this.merchantName = merchantName;
        this.offerSource = offerSource;
        this.estimatedCPC = estimatedCPC;
        this.availability = availability;
        this.deliveryCost = deliveryCost;
        this.ean = ean;
    }

    public List<String> getEan() {
        return ean;
    }

    public Double getDeliveryCost() {
        return deliveryCost;
    }

    public String getMerchant() {
        return merchant;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getUrl() {
        return url;
    }

    public String getMerchantImageUrl() {
        return merchantImageUrl;
    }

    public String getCurrency() {
        return currency;
    }

    public String getId() {
        return id;
    }

    public String getCategory() {
        return category;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Double getPrice() {
        return price;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public String getOfferSource() {
        return offerSource;
    }

    public Double getEstimatedCPC() {
        return estimatedCPC;
    }

    public String getAvailability() {
        return availability;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Offer{");
        sb.append("merchant='").append(merchant).append('\'');
        sb.append(", imageUrl='").append(imageUrl).append('\'');
        sb.append(", url='").append(url).append('\'');
        sb.append(", merchantImageUrl='").append(merchantImageUrl).append('\'');
        sb.append(", currency='").append(currency).append('\'');
        sb.append(", id='").append(id).append('\'');
        sb.append(", category='").append(category).append('\'');
        sb.append(", title='").append(title).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append(", price=").append(price);
        sb.append(", manufacturer='").append(manufacturer).append('\'');
        sb.append(", merchantName='").append(merchantName).append('\'');
        sb.append(", offerSource='").append(offerSource).append('\'');
        sb.append(", estimatedCPC=").append(estimatedCPC);
        sb.append(", availability='").append(availability).append('\'');
        sb.append(", deliveryCost=").append(deliveryCost);
        sb.append(", ean=").append(ean);
        sb.append(", source='").append(source).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
