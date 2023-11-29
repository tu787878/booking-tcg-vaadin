package de.tcg.booking.entity;

public enum Role {
	ADMIN, MANAGER, CUSTOMER;

    @Override
    public String toString() {
        return name().charAt(0) + name().substring(1).toLowerCase();
    }
}
